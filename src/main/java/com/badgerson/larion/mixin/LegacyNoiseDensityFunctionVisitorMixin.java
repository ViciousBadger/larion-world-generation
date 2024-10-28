package com.badgerson.larion.mixin;

import net.minecraft.world.gen.densityfunction.DensityFunction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.badgerson.larion.Larion;
import com.badgerson.larion.NoiseConfigSeedHelper;
import net.minecraft.world.gen.noise.NoiseConfig;
import com.badgerson.larion.density_function_types.Worley;

@Mixin(targets = "net.minecraft.world.gen.noise.NoiseConfig$LegacyNoiseDensityFunctionVisitor")
public class LegacyNoiseDensityFunctionVisitorMixin {
    @Inject(method = "applyNotCached", at = @At("HEAD"), cancellable = true)
    private static void applyNotCachedMixin(DensityFunction densityFunction,
            CallbackInfoReturnable<DensityFunction> ci) {
        if (densityFunction instanceof Worley worley) {
            var seed = NoiseConfigSeedHelper.LAST_SEED;
            var h = densityFunction.getCodecHolder().hashCode();

            Larion.LOGGER.info("Worley noise has been seeded with " + seed);
            Larion.LOGGER.info("codec hash " + h);
            ci.setReturnValue(new Worley(worley.xzScale(), worley.yScale(), worley.xShift(), worley.zShift(),
                    worley.createSampler(seed)));

        }
    }
}
