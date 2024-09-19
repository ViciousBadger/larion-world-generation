package com.badgerson.larion.density_function_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.articdive.jnoise.core.util.vectors.Vector4D;
import de.articdive.jnoise.generators.noise_parameters.distance_functions.DistanceFunctionType;
import de.articdive.jnoise.generators.noise_parameters.return_type_functions.ReturnDistanceFunction;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.transformers.domain_warp.DomainWarpTransformer;
import de.articdive.jnoise.transformers.scale.ScaleTransformer;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public class Worley
        implements DensityFunction.Base {

    private double warpScale;
    private double warpAmount;
    private double xzScale;
    private double yScale;

    public double getWarpScale() {
        return warpScale;
    }

    public double getWarpAmount() {
        return warpAmount;
    }

    public double getXZScale() {
        return xzScale;
    }

    public double getYScale() {
        return yScale;
    }

    private JNoise sampler;

    public static final MapCodec<Worley> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.DOUBLE.fieldOf("warp_scale").forGetter(Worley::getWarpScale),
                    Codec.DOUBLE.fieldOf("warp_amount").forGetter(Worley::getWarpAmount),
                    Codec.DOUBLE.fieldOf("xz_scale").forGetter(Worley::getXZScale),
                    Codec.DOUBLE.fieldOf("y_scale").forGetter(Worley::getYScale))
                    .apply(instance, Worley::new));
    public static final CodecHolder<Worley> CODEC = DensityFunctionTypes.holderOf(MAP_CODEC);

    record CoolDistanceFunction() implements ReturnDistanceFunction {
        @Override
        public double applyAsDouble(double[] value) {
            return value[0] / value[2];
        }

        @Override
        public boolean isValidArrayLength(int depth) {
            return depth >= 3;
        }
    }

    public Worley(double warpScale, double warpAmount, double xzScale, double yScale) {
        this.warpScale = warpScale;
        this.warpAmount = warpAmount;
        this.xzScale = xzScale;
        this.yScale = yScale;

        var domainWarp = JNoise.newBuilder().setNoiseSource(FastSimplexNoiseGenerator.newBuilder()).scale(warpScale);

        this.sampler = JNoise.newBuilder()
                .worley(WorleyNoiseGenerator.newBuilder().setSeed(12345).setDepth(3)
                        .setDistanceFunction(DistanceFunctionType.EUCLIDEAN_SQUARED)
                        .setReturnDistanceFunction(new CoolDistanceFunction()).build())
                .addDetailedTransformer(DomainWarpTransformer.newBuilder()
                        .setNoiseSource(domainWarp)
                        .setWarpingVector(new Vector4D(warpAmount, warpAmount, warpAmount, 0.0)).build())
                .addSimpleTransformer(new ScaleTransformer(xzScale, yScale, xzScale, 1.0))
                .clamp(0.0, 1.0).build();
    }

    @Override
    public double sample(NoisePos pos) {
        return sampler.evaluateNoise(pos.blockX(), pos.blockY(), pos.blockZ());
    }

    @Override
    public double minValue() {
        return -1.0;
    }

    @Override
    public double maxValue() {
        return 1.0;
    }

    @Override
    public CodecHolder<? extends DensityFunction> getCodecHolder() {
        return CODEC;
    }
}
