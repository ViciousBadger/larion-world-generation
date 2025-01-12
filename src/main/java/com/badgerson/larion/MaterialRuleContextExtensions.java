package com.badgerson.larion;

import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public interface MaterialRuleContextExtensions {
  public MaterialRules.BooleanSupplier getSomewhatSteepSlopePredicate();
}
