package com.badgerson.larion.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {
    @Inject(method = "createFluidLevelSampler", at = @At("HEAD"), cancellable = true)
    private static void createFluidLevelSampler(ChunkGeneratorSettings settings, CallbackInfoReturnable<AquiferSampler.FluidLevelSampler> ci) {
        // This mixin modifies the aquifer generator so that the "bedrock lava
        // level" (in vanilla hardcoded to -54) is instead determined by the world's
        // minimum Y level, allowing for deeper caves without them being flooded.
        // Ideally the lava sea level would be a field in the noise settings
        // like normal sea level, but this is easier to implement
        var lavaSeaLevel = settings.generationShapeConfig().minimumY() + 10;
		AquiferSampler.FluidLevel fluidLevel = new AquiferSampler.FluidLevel(lavaSeaLevel, Blocks.LAVA.getDefaultState());
		int i = settings.seaLevel();
		AquiferSampler.FluidLevel fluidLevel2 = new AquiferSampler.FluidLevel(i, settings.defaultFluid());
		// AquiferSampler.FluidLevel fluidLevel3 = new AquiferSampler.FluidLevel(DimensionType.MIN_HEIGHT * 2, Blocks.AIR.getDefaultState());
        ci.setReturnValue((x, y, z) -> y < Math.min(lavaSeaLevel, i) ? fluidLevel : fluidLevel2);
    }
}
