package com.badgerson.larion.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.Heightmap;

import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

@Mixin(MaterialRules.MaterialRuleContext.SteepSlopePredicate.class)
public class SteepSlopePredicateMixin extends MaterialRules.HorizontalLazyAbstractPredicate {
 protected SteepSlopePredicateMixin(MaterialRules.MaterialRuleContext materialRuleContext) {
  super(materialRuleContext);
 }

 @Override
 protected boolean test() {
  int x = this.context.blockX & 15;
  int z = this.context.blockZ & 15;

  Chunk chunk = this.context.chunk;

  int here = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, z);

  final int STEEP_THRESHOLD = 3;

	int south = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, Math.max(z - 1, 0));
	if (here - south > STEEP_THRESHOLD) {
		return true;
	} else {
		int north = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, Math.min(z + 1, 15));
		if (here - north > STEEP_THRESHOLD) {
			return true;
		} else {

			int west = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, Math.max(x - 1, 0), z);
			if (here - west > STEEP_THRESHOLD) {
				return true;
			} else {
				int east = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, Math.min(x + 1, 15), z);
				return here - east > STEEP_THRESHOLD;
			}
		}
	}
}
}
