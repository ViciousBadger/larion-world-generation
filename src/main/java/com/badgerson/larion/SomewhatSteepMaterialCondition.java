package com.badgerson.larion;

import com.mojang.serialization.MapCodec;

import net.minecraft.util.dynamic.CodecHolder;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public enum SomewhatSteepMaterialCondition implements MaterialRules.MaterialCondition {
  INSTANCE;

  static final CodecHolder<SomewhatSteepMaterialCondition> CODEC = CodecHolder.of(MapCodec.unit(INSTANCE));

  @Override
  public CodecHolder<? extends MaterialRules.MaterialCondition> codec() {
    return CODEC;
  }

  public MaterialRules.BooleanSupplier apply(MaterialRules.MaterialRuleContext materialRuleContext) {
    return ((MaterialRuleContextExtensions)(Object)materialRuleContext).getSomewhatSteepSlopePredicate();
  }
}
