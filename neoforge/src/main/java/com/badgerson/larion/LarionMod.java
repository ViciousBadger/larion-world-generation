package com.badgerson.larion;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import com.badgerson.larion.density_function_types.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;

@Mod(Constants.MOD_ID)
public class LarionMod {

	private static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister
			.create(Registries.DENSITY_FUNCTION_TYPE, Constants.MOD_ID);

	public static final Supplier<MapCodec<? extends DensityFunction>> DIV = DENSITY_FUNCTION_TYPES.register("div",
			() -> {
				return Division.CODEC.codec();
			});

	public static final Supplier<MapCodec<? extends DensityFunction>> SQRT = DENSITY_FUNCTION_TYPES
			.register("sqrt", () -> {
				return Sqrt.CODEC.codec();
			});

	public static final Supplier<MapCodec<? extends DensityFunction>> SIGNUM = DENSITY_FUNCTION_TYPES
			.register("signum", () -> {
				return Signum.CODEC.codec();
			});

	public static final Supplier<MapCodec<? extends DensityFunction>> SINE = DENSITY_FUNCTION_TYPES
			.register("sine", () -> {
				return Sine.CODEC.codec();
			});

	public static final Supplier<MapCodec<? extends DensityFunction>> X_COORD = DENSITY_FUNCTION_TYPES
			.register("x", () -> {
				return XCoord.CODEC.codec();
			});

	public static final Supplier<MapCodec<? extends DensityFunction>> Z_COORD = DENSITY_FUNCTION_TYPES
			.register("z", () -> {
				return ZCoord.CODEC.codec();
			});

	public static final Supplier<MapCodec<? extends DensityFunction>> FLAT_DOMAIN_WARP = DENSITY_FUNCTION_TYPES
			.register("flat_domain_warp", () -> {
				return FlatDomainWarp.CODEC.codec();
			});

	private static final DeferredRegister<MapCodec<? extends ConditionSource>> MATERIAL_CONDITIONS = DeferredRegister
			.create(Registries.MATERIAL_CONDITION, Constants.MOD_ID);

	public static final Supplier<MapCodec<? extends ConditionSource>> SOMEWHAT_STEEP = MATERIAL_CONDITIONS
			.register("somewhat_steep", () -> {
				return SomewhatSteepMaterialCondition.CODEC.codec();
			});

	public LarionMod(IEventBus eventBus) {
		DENSITY_FUNCTION_TYPES.register(eventBus);
		MATERIAL_CONDITIONS.register(eventBus);

		// This method is invoked by the NeoForge mod loader when it is ready
		// to load your mod. You can access NeoForge and Common code in this
		// project.

		// Use NeoForge to bootstrap the Common mod.
		CommonClass.init();

	}
}
