package com.badgerson.larion;

import net.minecraft.world.level.levelgen.SurfaceRules;

public interface MaterialRuleContextExtensions {
  public SurfaceRules.Condition getSomewhatSteepSlopePredicate();
}
