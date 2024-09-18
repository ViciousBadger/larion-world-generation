package com.badgerson.larion.density_function_types;

import com.badgerson.larion.Larion;
import com.badgerson.larion.util.FastNoiseLite;
import com.badgerson.larion.util.WorleyUtil;
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

public record Worley(float frequency, float yScale, DensityFunction shiftX, DensityFunction shiftY,
        DensityFunction shiftZ)
        implements DensityFunction {

    public static final MapCodec<Worley> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("frequency").forGetter(Worley::frequency),
                    Codec.FLOAT.fieldOf("y_scale").forGetter(Worley::yScale),
                    DensityFunction.FUNCTION_CODEC.fieldOf("shift_x").forGetter(Worley::shiftX),
                    DensityFunction.FUNCTION_CODEC.fieldOf("shift_y").forGetter(Worley::shiftY),
                    DensityFunction.FUNCTION_CODEC.fieldOf("shift_z").forGetter(Worley::shiftZ))
                    .apply(instance, Worley::new));
    public static final CodecHolder<Worley> CODEC = DensityFunctionTypes.holderOf(MAP_CODEC);

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
    private static final WorleyUtil noiseWrapper = new WorleyUtil();

    // public Cellular {
    // noiseWrapper.noise.SetFrequency(frequency);
    // noiseWrapper.noise.SetSeed(seed);
    // // Larion.LOGGER.info("confiruged ceullular noise");
    // }

    @Override
    public double sample(NoisePos pos) {
        // Larion.LOGGER.info(Float.toString(result));
        float x = (pos.blockX() + (float) shiftX.sample(pos)) * frequency;
        float z = (pos.blockZ() + (float) shiftZ.sample(pos)) * frequency;
        float y = (pos.blockY() + (float) shiftY.sample(pos)) * frequency * yScale;
        float val = noiseWrapper.SingleCellular3Edge(x, y, z);
        // Larion.LOGGER.info(Float.toString(val));
        return val;
        // return this.noise.sample((double)pos.blockX() * this.xzScale,
        // (double)pos.blockY() * this.yScale, (double)pos.blockZ() * this.xzScale);
    }

    @Override
    public void fill(double[] densities, EachApplier applier) {
        applier.fill(densities, this);
    }

    @Override
    public DensityFunction apply(DensityFunctionVisitor visitor) {
        return visitor.apply(new Worley(this.frequency, this.yScale, this.shiftX, this.shiftY, this.shiftZ));
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
