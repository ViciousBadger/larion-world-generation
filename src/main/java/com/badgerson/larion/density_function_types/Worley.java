package com.badgerson.larion.density_function_types;

import org.jspecify.annotations.Nullable;

import com.badgerson.larion.Larion;
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

public record Worley(double warpScale, double warpAmount, double xzScale, double yScale, @Nullable JNoise sampler)
        implements DensityFunction.Base {

    public static final MapCodec<Worley> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.DOUBLE.fieldOf("warp_scale").forGetter(Worley::warpScale),
                    Codec.DOUBLE.fieldOf("warp_amount").forGetter(Worley::warpAmount),
                    Codec.DOUBLE.fieldOf("xz_scale").forGetter(Worley::xzScale),
                    Codec.DOUBLE.fieldOf("y_scale").forGetter(Worley::yScale))
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
        this(warpScale, warpAmount, xzScale, yScale, null);
    }

    public JNoise createSampler(long seed) {
        var domainWarp = JNoise.newBuilder().setNoiseSource(FastSimplexNoiseGenerator.newBuilder()).scale(warpScale);
        return JNoise.newBuilder()
                .worley(WorleyNoiseGenerator.newBuilder().setSeed(seed).setDepth(3)
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
        // if (sampler != null) {
        //     Larion.LOGGER.info("Sampler is here");
        // } else {
        //     Larion.LOGGER.info("Sampler not is here");
        // }
        return sampler == null ? 0.0 : sampler.evaluateNoise(pos.blockX(), pos.blockY(), pos.blockZ());
    }

    @Override
    public double minValue() {
        return 0.0;
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
