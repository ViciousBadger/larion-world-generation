package com.badgerson.larion.mixin;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.noise.NoiseConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.badgerson.larion.NoiseConfigSeedHelper;

@Mixin(NoiseConfig.class)
public class NoiseConfigMixin {

    @Inject(method = "<init>", at = @At("HEAD"))
    private static void constructorHead(ChunkGeneratorSettings chunkGeneratorSettings,
            RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParametersLookup, long seed,
            CallbackInfo ci) {
        NoiseConfigSeedHelper.LAST_SEED = seed;
    }
}
