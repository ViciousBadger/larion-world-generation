package com.badgerson.larion.density_function_types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

public record Sqrt(DensityFunction df) implements DensityFunctions.PureTransformer{

    private static final MapCodec<Sqrt> MAP_CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument").forGetter(Sqrt::df))
                    .apply(instance, (Sqrt::new)));
    public static final KeyDispatchDataCodec<Sqrt> CODEC = DensityFunctions.makeCodec(MAP_CODEC);

    @Override
    public DensityFunction input() {
        return this.df;
    }

    @Override
    public double transform(double density) {
        if (density <= 0) {
            return 0;
        }
        return Math.sqrt(density);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
        return new Sqrt(this.df.mapAll(visitor));
    }

    @Override
    public double minValue() {
        return Math.min(0, Math.sqrt(this.df.minValue()));
    }

    @Override
    public double maxValue() {
        return Math.max(0, Math.sqrt(this.df.maxValue()));
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
