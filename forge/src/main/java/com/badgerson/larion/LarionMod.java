package com.badgerson.larion;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.badgerson.larion.density_function_types.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

@Mod(Constants.MOD_ID)
public class LarionMod {

	private static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister
			.create(Registries.DENSITY_FUNCTION_TYPE, Constants.MOD_ID);

	public static final RegistryObject<MapCodec<? extends DensityFunction>> DIV = DENSITY_FUNCTION_TYPES.register("div",
			() -> {
				return Division.CODEC.codec();
			});

	public static final RegistryObject<MapCodec<? extends DensityFunction>> SQRT = DENSITY_FUNCTION_TYPES
			.register("sqrt", () -> {
				return Sqrt.CODEC.codec();
			});

	public static final RegistryObject<MapCodec<? extends DensityFunction>> SIGNUM = DENSITY_FUNCTION_TYPES
			.register("signum", () -> {
				return Signum.CODEC.codec();
			});

	public static final RegistryObject<MapCodec<? extends DensityFunction>> SINE = DENSITY_FUNCTION_TYPES
			.register("sine", () -> {
				return Sine.CODEC.codec();
			});

	public static final RegistryObject<MapCodec<? extends DensityFunction>> X_COORD = DENSITY_FUNCTION_TYPES
			.register("x", () -> {
				return XCoord.CODEC.codec();
			});

	public static final RegistryObject<MapCodec<? extends DensityFunction>> Z_COORD = DENSITY_FUNCTION_TYPES
			.register("z", () -> {
				return ZCoord.CODEC.codec();
			});

	public static final RegistryObject<MapCodec<? extends DensityFunction>> FLAT_DOMAIN_WARP = DENSITY_FUNCTION_TYPES
			.register("flat_domain_warp", () -> {
				return FlatDomainWarp.CODEC.codec();
			});

	private static final DeferredRegister<MapCodec<? extends ConditionSource>> MATERIAL_CONDITIONS = DeferredRegister
			.create(Registries.MATERIAL_CONDITION, Constants.MOD_ID);

	public static final RegistryObject<MapCodec<? extends ConditionSource>> SOMEWHAT_STEEP = MATERIAL_CONDITIONS
			.register("somewhat_steep", () -> {
				return SomewhatSteepMaterialCondition.CODEC.codec();
			});

	public LarionMod() {
		// This method is invoked by the Forge mod loader when it is ready
		// to load your mod. You can access Forge and Common code in this
		// project.

		DENSITY_FUNCTION_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		MATERIAL_CONDITIONS.register(FMLJavaModLoadingContext.get().getModEventBus());

		// Use Forge to bootstrap the Common mod.
		CommonClass.init();
	}
}
