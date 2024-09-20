package com.badgerson.larion;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badgerson.larion.density_function_types.*;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Larion implements ModInitializer {
    public static final String MOD_ID = "larion";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "div"), Division.CODEC.codec());
        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "signum"), Signum.CODEC.codec());
        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "sine"), Sine.CODEC.codec());
        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "z"), ZCoord.CODEC.codec());
        Registry.register(Registries.DENSITY_FUNCTION_TYPE, Identifier.of(MOD_ID, "worley"), Worley.CODEC.codec());
        LOGGER.info("Registered extra density functions!");
    }
}
