package com.badgerson.larion.density_function_types;

import com.badgerson.larion.util.FastNoiseLite;
import com.badgerson.larion.util.FastNoiseLite.NoiseType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

public record Cellular(int seed, float frequency, double xzScale, double yScale)
		implements DensityFunction {
		public static final MapCodec<Cellular> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
						Codec.INT.fieldOf("seed").forGetter(Cellular::seed),
						Codec.FLOAT.fieldOf("frequency").forGetter(Cellular::frequency),
						Codec.DOUBLE.fieldOf("xz_scale").forGetter(Cellular::xzScale),
						Codec.DOUBLE.fieldOf("y_scale").forGetter(Cellular::yScale)
					)
					.apply(instance, Cellular::new)
		);
		public static final CodecHolder<Cellular> CODEC = DensityFunctionTypes.holderOf(MAP_CODEC);

		@Override
		public double sample(NoisePos pos) {
            var noise = new FastNoiseLite();
            noise.SetNoiseType(NoiseType.Cellular);
            noise.SetSeed(seed);
            noise.SetFrequency(frequency);
            return noise.GetNoise(pos.blockX() * xzScale, pos.blockY() * yScale, pos.blockZ() * xzScale);
			// return this.noise.sample((double)pos.blockX() * this.xzScale, (double)pos.blockY() * this.yScale, (double)pos.blockZ() * this.xzScale);
		}

		@Override
		public void fill(double[] densities, EachApplier applier) {
			applier.fill(densities, this);
		}

		@Override
		public DensityFunction apply(DensityFunctionVisitor visitor) {
			return visitor.apply(new Cellular(this.seed, this.frequency, this.xzScale, this.yScale));
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
