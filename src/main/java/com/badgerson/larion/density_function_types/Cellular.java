package com.badgerson.larion.density_function_types;

import com.badgerson.larion.Larion;
import com.badgerson.larion.util.FastNoiseLite;
import com.badgerson.larion.util.FastNoiseLite.CellularDistanceFunction;
import com.badgerson.larion.util.FastNoiseLite.CellularReturnType;
import com.badgerson.larion.util.FastNoiseLite.DomainWarpType;
import com.badgerson.larion.util.FastNoiseLite.NoiseType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public record Cellular(int seed, float frequency, double yScale)
        implements DensityFunction {

    public static final MapCodec<Cellular> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("seed").forGetter(Cellular::seed),
                    Codec.FLOAT.fieldOf("frequency").forGetter(Cellular::frequency),
                    Codec.DOUBLE.fieldOf("y_scale").forGetter(Cellular::yScale))
                    .apply(instance, Cellular::new));
    public static final CodecHolder<Cellular> CODEC = DensityFunctionTypes.holderOf(MAP_CODEC);

    public static class CellNoiseWrapper {
        public FastNoiseLite noise;

        public CellNoiseWrapper() {
            noise = new FastNoiseLite();
            noise.SetNoiseType(NoiseType.Cellular);
            noise.SetCellularDistanceFunction(CellularDistanceFunction.EuclideanSq);
            noise.SetCellularReturnType(CellularReturnType.Distance2Div);
            noise.SetDomainWarpType(DomainWarpType.OpenSimplex2);
            noise.SetDomainWarpAmp(30.0f);
            Larion.LOGGER.info("initialized noise");
        }
    }
    private static final CellNoiseWrapper noiseWrapper = new CellNoiseWrapper();

    public Cellular {
        noiseWrapper.noise.SetFrequency(frequency);
        noiseWrapper.noise.SetSeed(seed);
        // Larion.LOGGER.info("confiruged ceullular noise");
    }

    @Override
    public double sample(NoisePos pos) {
        // Larion.LOGGER.info(Float.toString(result));
        return noiseWrapper.noise.GetNoise(pos.blockX(), pos.blockY() * yScale, pos.blockZ());
        // return this.noise.sample((double)pos.blockX() * this.xzScale,
        // (double)pos.blockY() * this.yScale, (double)pos.blockZ() * this.xzScale);
    }

    @Override
    public void fill(double[] densities, EachApplier applier) {
        applier.fill(densities, this);
    }

    @Override
    public DensityFunction apply(DensityFunctionVisitor visitor) {
        return visitor.apply(new Cellular(this.seed, this.frequency, this.yScale));
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
