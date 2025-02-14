package com.badgerson.larion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

@Mixin(CaveWorldCarver.class)
public class CaveWorldCarverMixin {
  @Inject(method = "getCaveBound", at = @At("HEAD"), cancellable = true)
  private static void getCaveBoundOverride(CallbackInfoReturnable<Integer> ci) {
    // Vanilla value: 15
    // https://www.minecraftforum.net/forums/minecraft-java-edition/recent-updates-and-snapshots/381672-it-seems-that-the-underground-is-no-longer-swiss?comment=27
    ci.setReturnValue(25);
  }
}
