package badgerson.larion.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import badgerson.larion.Larion;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {
    /**
	 * @author Badgerson
	 * @reason Move the hardcoded -54 lava sea level to fit a -128 world depth
	 */
    @Overwrite
    private static AquiferSampler.FluidLevelSampler createFluidLevelSampler(ChunkGeneratorSettings settings) {
		AquiferSampler.FluidLevel fluidLevel = new AquiferSampler.FluidLevel(-118, Blocks.LAVA.getDefaultState());
		int i = settings.seaLevel();
		AquiferSampler.FluidLevel fluidLevel2 = new AquiferSampler.FluidLevel(i, settings.defaultFluid());
		// AquiferSampler.FluidLevel fluidLevel3 = new AquiferSampler.FluidLevel(DimensionType.MIN_HEIGHT * 2, Blocks.AIR.getDefaultState());
        Larion.LOGGER.info("we injected the codes");
		return (x, y, z) -> y < Math.min(-118, i) ? fluidLevel : fluidLevel2;
    }
}
