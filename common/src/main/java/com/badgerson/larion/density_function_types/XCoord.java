// Original credit: https://github.com/klinbee/More-Density-Functions (thanks)
package com.badgerson.larion.density_function_types;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public record XCoord() implements DensityFunction.SimpleFunction {

    public static final KeyDispatchDataCodec<XCoord> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(new XCoord()));

    @Override
    public double compute(DensityFunction.FunctionContext context) {
        return Math.min(Math.max(context.blockX(), minValue()), maxValue());
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
