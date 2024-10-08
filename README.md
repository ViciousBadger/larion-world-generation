![Banner](images/banner.jpg)

# Larion, for Minecraft 1.19.2-1.21.1

Larion is a major overhaul of the overworld generation in Minecraft that
drastically alters terrain shapes and biome placement.

I wanted the terrain to be more visually stunning, reminiscent of what you'd
find in epic fantasy worldbuilding, while still being small-scale and unpredictable
enough to be interesting to explore in survival mode.

The biomes themselves are not altered at all, making Larion fully compatible
with most biome overhauls. See below for a list of compability notes
and potential solutions.

## Features

- The world is split into large continents with plenty of smaller islands in-between. Continent sizes and
shapes vary wildly.
- Temperature changes as you move north or south (z-axis).
    - If you travel far enough, it wraps back around.
    - Travelling east or west lets you stay in roughly the same temperature.
    - "Tropical" biomes (jungle, savanna etc) generate at the "equator" meaning
    at the highest temperature zones.
- Mountains form large "chains" roughly similar to continental plates in real life.
    - They are also taller, usually between 200 and 300 blocks in height, but in rare cases even higher.
    - Jagged peaks are less "jagged" than usual, shaped more like real mountains.
- World height is increased from 384 to 512 to fit the taller mountains and deeper caves
    - Build height limit is 384.
    - Lowest point is -128.
- Terrain slopes gently upwards as you go further
inland, on top of plenty small-scale height variations everywhere.
- Smaller biomes with less predictable shapes and placement.
    - "Humidity" zones are weirder, sometimes small, sometimes large.
    - Swamps and mangroves can only appear along coasts and rivers.
    - Windswept terrain is more common and can appear anywhere.
- Long, winding rivers that carve though terrain.
    - In low terrain they form "saddle valleys"
    - In tall terrain they will keep flowing as underground rivers.
    - Sometimes rivers form strange "knots" that look like lakes.
- Mushroom islands are more common, smaller and oddly shaped.
- Caves are deeper and stranger.
    - They use a different kind of noise, directly inspired by the abandoned
    "Worley's Caves" mod.
    - Caves are extremely labyrinthian and dead ends are common. Mostly narrow
    paths and mid-sized chambers.. unless you venture deep enough..
- Numerous smaller tweaks
    - Dunes in desert biomes
    - Less ugly cliffs at coastlines
    - Less taiga in frozen zones
    - More jungle in tropical zones
    - Badlands mountains have plateaus instead of peaks
    - Probably more..!

## Known issues

- Some seeds will spawn you in water or on a tiny island in the middle of
nowhere. If you want a bit more predictability when creating a world, I would
recommend using [World Preview](https://modrinth.com/mod/world-preview).
- World generation is not as fast as before (roughly 30% slower in my
experience) because the new density functions are much more
complex. However, by using these Fabric mods you can counterbalance the slowness:
    - [C2ME](https://modrinth.com/mod/c2me-fabric) (MAJOR performance boost, scales with CPU core count)
    - [Noisium](https://modrinth.com/mod/noisium)
    - [Faster Random](https://modrinth.com/mod/faster-random)
    - [FerriteCore](https://modrinth.com/mod/ferrite-core)

## List of compability notes with other worldgen datapacks/mods

### Geophilic

**100% compatible**.

### Arboria

**100% compatible**.

### William Wyther's Overhauled Overworld

**99% compatible**.

So far this is my favorite worldgen mod to use with Larion, they go together
perfectly.

If WWOO is loaded **after** Larion, mountains taller than Y=320 will be
"chopped" beacuse WWOO overwrites the file that determines world height. There
are two ways to solve this:

- By using both Larion and WWOO as datapacks instead of mods, you can manually
change the load order so that Larion loads last and overwrites the world height
correctly.
- By unpacking the WWOO .zip file and removing **all three** files named
overworld.json, the issue is now gone and load order is no longer important!
- Be sure to also use **Cliffs and Coves** and **Navigable Rivers** by the same
  author, as they can greatly improve coasts and rivers.

### William Wyther's Expanded Ecosphere

**100% compatible**. WWEE only makes changes to files that Larion
does not touch so they can be loaded together without issues.

These go very well together, biome diversity is significantly better.

### Terralith

**50% compatible**. Terralith overwrites a few of the same files. By loading it
before Larion you will get Terralith's new biomes and Larion's terrain, which is
quite interesting, but you'll miss out on Terralith's impressive biome-specific
terrain features.

### Continents

**Incompatible**. Both overwrite density_function/overworld/continents.json, but
you can still use them together if you prefer the continents of Continents.

### Tectonic, Lithosphere, Cascades, Eldor

**Incompatible**. All these packs modify the same core files as Larion, altering
terrain generation with different goals in mind. Whichever mod is loaded last
will overwrite 99% of the features added by any of the other mods.

## Changes to vanilla data files

To make it easier to create compability patches etc, here is a list of
overwritten files along with descriptions what has been changed.

- `dimension_type/overworld.json` and `dimension_type/overworld_caves.json`
    - Changed `height` and `logical_height` to 512
- `worldgen/noise_settings/overworld.json`
    - Changed `noise/height` to 512
    - Two changes inside the giant `final_density` function
        - Modified the `y_clamped_gradient` that previously limited mountain height
          to 240-256 to be at 420-447
        - Inject the `larion:overworld/river_noodle` density function inside/near `blend_density`
    - Changed `fluid_level_floodedness` and `fluid_level_spread` using `larion:overworld/river_noodle_flood` to make sure river caves are always "flooded" (full of water at sea level)
    - Changed `temperature` and `vegetation` to point at my own density
    functions instead of inline code
- Six density functions in `worldgen/density_functions/overworld` have been overwritten
    - `continents`, `erosion` and `ridges` have all been rewired to point at
    density functions in the `larion` scope
    - `depth` was edited
        - Max height changed to 448 from 320
        - Also added an extra negative number to offset to make the sea align correctly
        - Also "squashed" the offset function to 0.75 of its original height,
        without this hills would be too tall (looks silly)
        - Also added `extra_offset` (continental slopes) on top of vanilla offset
    - `sloped_cheese` was been modified
        - Changed "jaggedness" scale
        - Injected "dunes" noise
        - Added an extra "factor-factor" that reduces factor at mushroom islands and increases it at windswept terrain 
- `cave` and `cave_extra_underground` carvers modified
- Ancient cities modified to move them further down and make them a bit rarer
since mountains are more common.

# Special thanks to

- alkexr, creator of
[Eldor](https://www.planetminecraft.com/data-pack/eldor/). Eldor was a major
insipration for this pack and I used many of its clever density functions as a reference.
for several changes.
- jacobsjo, creator of [Saddle Valley Rivers](https://www.planetminecraft.com/data-pack/saddle-valley-rivers-canyons-and-underground-rivers-1-18-2-only/)
- devpelux, creator of [X-Mountains](https://modrinth.com/datapack/xmountains)
- Apollo, creator of [Deeper oceans](https://modrinth.com/datapack/deeper-oceans) and [Tectonic](https://modrinth.com/datapack/tectonic)
- Klinbee, creator of [More Density Functions](https://modrinth.com/mod/more-density-functions)

# Footnotes

All pictures in this readme and on the modrinth page were taken using the
[Distant Horizons](https://modrinth.com/mod/distanthorizons) mod to see very far.

Larion is licensed under Apache 2.0, meaning you are free to modify and use the
pack as you wish. You can freely use any individual parts in your own
mod or datapack. However, if you choose to redistribute Larion, please be sure to
include the copyright (LICENSE.md) file, a link to this page and also state any
significant changes made.

The current version took about a month of precious free time to make.
If you like the project and want to send me a donation, here is a Paypal link:

https://www.paypal.com/donate/?hosted_button_id=L2WKHTDJ4DANU

.. or alternatively send some Bitcoin to bc1qk5688pjsy228zkrda5e9w43wzn0zye4w0ygej4
