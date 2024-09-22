package com.badgerson.larion.density_function_types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public record Sqrt(DensityFunction df) implements DensityFunctionTypes.Unary {

    private static final MapCodec<Sqrt> MAP_CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(DensityFunction.FUNCTION_CODEC.fieldOf("argument").forGetter(Sqrt::df))
                    .apply(instance, (Sqrt::new)));
    public static final CodecHolder<Sqrt> CODEC = DensityFunctionTypes.holderOf(MAP_CODEC);

    @Override
    public DensityFunction input() {
        return this.df;
    }

    @Override
    public double apply(double density) {
        if (density <= 0) {
            return 0;
        }
        return Math.sqrt(density);
    }

    @Override
    public DensityFunction apply(DensityFunctionVisitor visitor) {
        return new Sqrt(this.df.apply(visitor));
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
    public CodecHolder<? extends DensityFunction> getCodecHolder() {
        return CODEC;
    }
}
