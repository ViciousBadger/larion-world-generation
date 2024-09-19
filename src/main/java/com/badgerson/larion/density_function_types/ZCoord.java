// Original credit: https://github.com/klinbee/More-Density-Functions (thanks)
package com.badgerson.larion.density_function_types;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;

public record ZCoord() implements DensityFunction.Base {

    public static final CodecHolder<ZCoord> CODEC = CodecHolder.of(MapCodec.unit(new ZCoord()));

    @Override
    public double sample(DensityFunction.NoisePos pos) {
        return Math.min(Math.max(pos.blockZ(),minValue()), maxValue());
    }

    @Override
    public double minValue() {
        return -30_000_000;
    }

    @Override
    public double maxValue() {
        return 30_000_000;
    }

    public CodecHolder<? extends DensityFunction> getCodecHolder() {
        return CODEC;
    }
}

