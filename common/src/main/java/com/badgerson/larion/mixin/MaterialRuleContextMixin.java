package com.badgerson.larion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.badgerson.larion.Larion;
import com.badgerson.larion.MaterialRuleContextExtensions;
import com.badgerson.larion.SomewhatSteepSlopePredicate;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.MaterialRules.BooleanSupplier;
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRuleContext;


@Mixin(MaterialRuleContext.class)
public abstract class MaterialRuleContextMixin implements MaterialRuleContextExtensions {

  public MaterialRules.BooleanSupplier somewhatSteepSlopePredicate;

  @Inject(
    method = "<init>",
    at = @At("TAIL")
  )
  public void constructorTail(CallbackInfo ci) {
    somewhatSteepSlopePredicate = new SomewhatSteepSlopePredicate((MaterialRuleContext)(Object)this);
  }

  @Override
  public BooleanSupplier getSomewhatSteepSlopePredicate() {
  	return somewhatSteepSlopePredicate;
  }
    
}
