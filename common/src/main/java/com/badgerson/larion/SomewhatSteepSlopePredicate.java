package com.badgerson.larion;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SomewhatSteepSlopePredicate extends SurfaceRules.LazyXZCondition{
	public SomewhatSteepSlopePredicate(SurfaceRules.Context context) {
		super(context);
	}

	@Override
	protected boolean compute() {

  int x = this.context.blockX & 15;
  int z = this.context.blockZ & 15;

  ChunkAccess chunk = this.context.chunk;

  int here = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);

  final int SOMEWHAT_STEEP_THRESHOLD = 1;

	int south = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, Math.max(z - 1, 0));
	if (here - south > SOMEWHAT_STEEP_THRESHOLD) {
		return true;
	} else {
		int north = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, Math.min(z + 1, 15));
		if (here - north > SOMEWHAT_STEEP_THRESHOLD) {
			return true;
		} else {

			int west = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, Math.max(x - 1, 0), z);
			if (here - west > SOMEWHAT_STEEP_THRESHOLD) {
				return true;
			} else {
				int east = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, Math.min(x + 1, 15), z);
				return here - east > SOMEWHAT_STEEP_THRESHOLD;
			}
		}
	}
  }
}
