package com.badgerson.larion.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseChunkGeneratorMixin {
    @Inject(method = "createFluidLevelSampler", at = @At("HEAD"), cancellable = true)
    private static void createFluidLevelSampler(NoiseGeneratorSettings settings, CallbackInfoReturnable<Aquifer.FluidPicker> ci) {
        // This mixin modifies the aquifer generator so that the "bedrock lava
        // level" (in vanilla hardcoded to -54) is instead determined by the world's
        // minimum Y level, allowing for deeper caves without them being flooded.
        // Ideally the lava sea level would be a field in the noise settings
        // like normal sea level, but this is easier to implement
        var lavaSeaLevel = settings.noiseSettings().minY() + 10;
		Aquifer.FluidStatus fluidLevel = new Aquifer.FluidStatus(lavaSeaLevel, Blocks.LAVA.defaultBlockState());
		int i = settings.seaLevel();
		Aquifer.FluidStatus fluidLevel2 = new Aquifer.FluidStatus(i, settings.defaultFluid());
		// AquiferSampler.FluidLevel fluidLevel3 = new AquiferSampler.FluidLevel(DimensionType.MIN_HEIGHT * 2, Blocks.AIR.getDefaultState());
        ci.setReturnValue((x, y, z) -> y < Math.min(lavaSeaLevel, i) ? fluidLevel : fluidLevel2);
    }
}
