package com.badgerson.larion.density_function_types;

import org.jspecify.annotations.Nullable;

import com.badgerson.larion.Larion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.articdive.jnoise.core.util.vectors.Vector4D;
import de.articdive.jnoise.generators.noise_parameters.distance_functions.DistanceFunction;
import de.articdive.jnoise.generators.noise_parameters.distance_functions.DistanceFunctionType;
import de.articdive.jnoise.generators.noise_parameters.return_type_functions.ReturnDistanceFunction;
import de.articdive.jnoise.generators.noise_parameters.return_type_functions.ReturnDistanceFunctionType;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.transformers.domain_warp.DomainWarpTransformer;
import de.articdive.jnoise.transformers.scale.ScaleTransformer;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public record Worley(double xzScale, double yScale, DensityFunction xShift, DensityFunction zShift,
        @Nullable JNoise sampler)
        implements DensityFunction {

    public static final MapCodec<Worley> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.DOUBLE.fieldOf("xz_scale").forGetter(Worley::xzScale),
                    Codec.DOUBLE.fieldOf("y_scale").forGetter(Worley::yScale),
                    DensityFunction.FUNCTION_CODEC.fieldOf("x_shift").forGetter(Worley::xShift),
                    DensityFunction.FUNCTION_CODEC.fieldOf("z_shift").forGetter(Worley::zShift))
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

    public Worley(double xzScale, double yScale, DensityFunction xShift, DensityFunction zShift) {
        this(xzScale, yScale, xShift, zShift, null);
    }

    public JNoise createSampler(long seed) {
        return JNoise.newBuilder()
                .worley(WorleyNoiseGenerator.newBuilder().setSeed(seed).setDepth(1)
                        .setDistanceFunction(DistanceFunctionType.EUCLIDEAN_SQUARED)
                        .setReturnDistanceFunction(ReturnDistanceFunctionType.DISTANCE_0).build())
                .clamp(0.0, 1.0).build();
    }

    @Override
    public double sample(NoisePos pos) {
        var xPos = pos.blockX() * xzScale + xShift.sample(pos);
        var zPos = pos.blockZ() * xzScale + zShift.sample(pos);

        return sampler == null ? 0.0
                : sampler.evaluateNoise(xPos, 0, zPos);
    }

    @Override
    public void fill(double[] densities, EachApplier applier) {
        applier.fill(densities, this);
    }

    @Override
    public DensityFunction apply(DensityFunctionVisitor visitor) {
        return visitor
                .apply(new Worley(this.xzScale, this.yScale, this.xShift.apply(visitor), this.zShift.apply(visitor), this.sampler));
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
