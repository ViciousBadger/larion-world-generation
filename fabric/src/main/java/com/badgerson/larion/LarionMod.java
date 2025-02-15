package com.badgerson.larion;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import com.badgerson.larion.density_function_types.*;

public class LarionMod implements ModInitializer {

  @Override
  public void onInitialize() {

    // This method is invoked by the Fabric mod loader when it is ready
    // to load your mod. You can access Fabric and Common code in this
    // project.

    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "div"), Division.CODEC.codec());
    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "sqrt"), Sqrt.CODEC.codec());
    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "signum"), Signum.CODEC.codec());
    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "sine"), Sine.CODEC.codec());
    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "x"), XCoord.CODEC.codec());
    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "z"), ZCoord.CODEC.codec());
    Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE,
        ResourceLocation.tryBuild(Constants.MOD_ID, "flat_domain_warp"), FlatDomainWarp.CODEC.codec());
    Registry.register(BuiltInRegistries.MATERIAL_CONDITION,
        ResourceLocation.tryBuild(Constants.MOD_ID, "somewhat_steep"),
        SomewhatSteepMaterialCondition.CODEC.codec());

    // Use Fabric to bootstrap the Common mod.
    Constants.LOG.info("Larion World Generation: Registered custom entries");
    CommonClass.init();
  }
}
