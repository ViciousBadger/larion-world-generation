// Original credit: https://github.com/klinbee/More-Density-Functions (thanks)
package com.badgerson.larion.density_function_types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

public record Sine(DensityFunction df) implements DensityFunctions.PureTransformer {

    private static final MapCodec<Sine> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument").forGetter(Sine::df)).apply(instance, (Sine::new)));
    public static final KeyDispatchDataCodec<Sine> CODEC  = DensityFunctions.makeCodec(MAP_CODEC);

    @Override
    public DensityFunction input() {
        return this.df;
    }

    @Override
    public double transform(double density) {
        return Math.sin(density);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return new Sine(this.df.mapAll(visitor));
    }

    @Override
    public double minValue() {
        return -1;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}

