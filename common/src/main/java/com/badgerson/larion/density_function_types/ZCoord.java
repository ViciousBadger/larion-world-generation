// Original credit: https://github.com/klinbee/More-Density-Functions (thanks)
package com.badgerson.larion.density_function_types;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public record ZCoord() implements DensityFunction.SimpleFunction {

    public static final KeyDispatchDataCodec<ZCoord> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(new ZCoord()));

    @Override
    public double compute(DensityFunction.FunctionContext context) {
        return Math.min(Math.max(context.blockZ(),minValue()), maxValue());
    }

    @Override
    public double minValue() {
        return -30_000_000;
    }

    @Override
    public double maxValue() {
        return 30_000_000;
    }

    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}

