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

import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public class Worley
        implements DensityFunction.Base {

    private float frequency;

    public float getFrequency() {
        return frequency;
    }

    private float yScale;

    public float getyScale() {
        return yScale;
    }

    private FastNoiseLite sampler;

    public Worley(float frequency, float yScale) {
        this.frequency = frequency;
        this.yScale = yScale;
        this.sampler = new FastNoiseLite();
        this.sampler.SetSeed(1234);
        this.sampler.SetFrequency(frequency);
        this.sampler.SetNoiseType(NoiseType.Cellular);
        this.sampler.SetCellularDistanceFunction(CellularDistanceFunction.EuclideanSq);
        this.sampler.SetCellularReturnType(CellularReturnType.Distance2Div);
        this.sampler.SetDomainWarpType(DomainWarpType.OpenSimplex2);
        this.sampler.SetDomainWarpAmp(30.0f);
    }

    public static final MapCodec<Worley> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("frequency").forGetter(Worley::getFrequency),
                    Codec.FLOAT.fieldOf("y_scale").forGetter(Worley::getyScale))
                    .apply(instance, Worley::new));
    public static final CodecHolder<Worley> CODEC = DensityFunctionTypes.holderOf(MAP_CODEC);

    @Override
    public double sample(NoisePos pos) {
        // Larion.LOGGER.info(Float.toString(result));
        float x = pos.blockX() * frequency;
        float z = pos.blockZ() * frequency;
        float y = pos.blockY() * frequency * yScale;
        return sampler.GetNoise(x, y, z);
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
