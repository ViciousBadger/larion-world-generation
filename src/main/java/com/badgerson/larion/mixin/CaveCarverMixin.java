package com.badgerson.larion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.carver.CaveCarver;

@Mixin(CaveCarver.class)
public class CaveCarverMixin {
    // @Inject(method = "getTunnelSystemWidth", at = @At("HEAD"), cancellable = true)
	// private void getTunnelSystemWidth(Random random, CallbackInfoReturnable<Float> ci) {
    //     ci.setReturnValue(20.0f);
    // }
}
