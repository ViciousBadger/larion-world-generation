package com.badgerson.larion.mixin;

import net.minecraft.world.gen.densityfunction.DensityFunction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.badgerson.larion.Larion;
import com.badgerson.larion.NoiseConfigSeedHelper;
import com.badgerson.larion.density_function_types.Worley;

@Mixin(targets = "net.minecraft.world.gen.noise.NoiseConfig$LegacyNoiseDensityFunctionVisitor")
public class LegacyNoiseDensityFunctionVisitorMixin {
    @Inject(method = "applyNotCached", at = @At("HEAD"), cancellable = true)
    private static void applyNotCachedMixin(DensityFunction densityFunction,
            CallbackInfoReturnable<DensityFunction> ci) {
        if (densityFunction instanceof Worley worley) {
                var seed = NoiseConfigSeedHelper.LAST_SEED;

            Larion.LOGGER.info("worley is found, and last seed is " + seed);
            ci.setReturnValue(new Worley(worley.warpScale(), worley.warpAmount(), worley.xzScale(), worley.yScale(),
                    worley.createSampler(seed)));

        }
    }
}
