package com.badgerson.larion.density_function_types;

import com.badgerson.larion.Larion;
import com.badgerson.larion.util.FastNoiseLite;
import com.badgerson.larion.util.WorleyUtil;
import net.minecraft.world.gen.densityfunction.DensityFunction.DensityFunctionVisitor;
import com.badgerson.larion.util.FastNoiseLite.CellularDistanceFunction;
import com.badgerson.larion.util.FastNoiseLite.CellularReturnType;
import com.badgerson.larion.util.FastNoiseLite.DomainWarpType;
import com.badgerson.larion.util.FastNoiseLite.NoiseType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.articdive.jnoise.generators.noise_parameters.distance_functions.DistanceFunctionType;
import de.articdive.jnoise.generators.noise_parameters.return_type_functions.ReturnDistanceFunction;
import de.articdive.jnoise.generators.noise_parameters.return_type_functions.ReturnDistanceFunctionType;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public class Worley
        implements DensityFunction.Base {

    private double frequency;

    public double getFrequency() {
        return frequency;
    }

    private double yScale;

    public double getyScale() {
        return yScale;
    }

    private JNoise sampler;

    public static final MapCodec<Worley> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.DOUBLE.fieldOf("frequency").forGetter(Worley::getFrequency),
                    Codec.DOUBLE.fieldOf("y_scale").forGetter(Worley::getyScale))
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

    public Worley(double frequency, double yScale) {
        this.frequency = frequency;
        this.yScale = yScale;

        this.sampler = JNoise.newBuilder()
                .worley(WorleyNoiseGenerator.newBuilder().setSeed(12345).setDepth(3)
                        .setDistanceFunction(DistanceFunctionType.EUCLIDEAN_SQUARED)
                        .setReturnDistanceFunction(new CoolDistanceFunction()).build())
                .scale(frequency).clamp(0.0, 1.0).build();
    }

    @Override
    public double sample(NoisePos pos) {
        // Larion.LOGGER.info(Float.toString(result));
        double x = pos.blockX() * frequency;
        double z = pos.blockZ() * frequency;
        double y = pos.blockY() * frequency * yScale;
        return sampler.evaluateNoise(x, y, z);
        // float val = noiseWrapper.SingleCellular3Edge(x, y, z);
        // Larion.LOGGER.info(Float.toString(val));
        // return val;
        // return this.noise.sample((double)pos.blockX() * this.xzScale,
        // (double)pos.blockY() * this.yScale, (double)pos.blockZ() * this.xzScale);
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
