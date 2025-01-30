package com.badgerson.larion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.badgerson.larion.MaterialRuleContextExtensions;
import com.badgerson.larion.SomewhatSteepSlopePredicate;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.levelgen.SurfaceRules;


@Mixin(SurfaceRules.Context.class)
public abstract class MaterialRuleContextMixin implements MaterialRuleContextExtensions {

  public SurfaceRules.Condition somewhatSteepSlopePredicate;

  @Inject(
    method = "<init>",
    at = @At("TAIL")
  )
  public void constructorTail(CallbackInfo ci) {
    somewhatSteepSlopePredicate = new SomewhatSteepSlopePredicate((SurfaceRules.Context)(Object)this);
  }

  @Override
  public SurfaceRules.Condition getSomewhatSteepSlopePredicate() {
  	return somewhatSteepSlopePredicate;
  }
    
}
