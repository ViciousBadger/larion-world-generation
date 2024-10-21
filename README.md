![Banner](images/banner.jpg)

# Larion, for Minecraft 1.20-1.21.1

Larion overhauls the overworld generation in Minecraft by
drastically altering terrain shapes and biome placement.

The name is a reference to the "Kingdom of Larion" in AI Dungeon stories, a
mysterious land with no pre-existing lore. In other words, it is a land where
every story must be imagined by you.

The world is larger in scale, more unpredictable and often more mountainous,
reminiscent of what you'd find in epic fantasy worldbuilding. I tried my best to
strike a balance between large- and small-scale features so that the worlds
generated feel vast, but are still interesting to explore in survival mode.

The biomes themselves are not altered at all, making Larion fully compatible
with most biome overhauls. See below for a list of compability notes
and potential solutions.

If you want a limited world size, check out the [Disc
World](https://modrinth.com/datapack/larion-one-continent) add-on datapack!

Please note that the mod is still in active development, future updates are nearly guaranteed to change how the world is generated. I would suggest not updating the mod on existing worlds unless your are okay with weird chunk borders.

## Features

- The world is split into large continents with plenty of smaller islands in-between.
- Temperature changes as you move north or south (z-axis).
    - If you travel far enough, it wraps back around.
    - Travelling east or west lets you stay in roughly the same temperature.
    - Tropical and subtropical temperature zones have been "swapped".
- Mountains form large "chains" while often being broader and taller than vanilla.
- World height is increased from 384 to 512 to fit the taller mountains and deeper caves
    - Build height limit is 384, lowest point is -128.
- An extra layer of gentle slopes on top of vanilla "offset" that makes flat areas sligthly hilly.
- Less predictable biome shapes and placement.
    - "Humidity" zones are weirder, sometimes small, sometimes large.
    - Swamps and mangroves can only appear near sea level (coasts and rivers).
    - Windswept terrain is more common and can appear anywhere.
- Long, winding rivers that carve though terrain.
    - Sometimes rivers form strange "knots" that look like lakes.
    - Large lava rivers also flow near the bedrock layer..
- Mushroom islands are more common, smaller and oddly shaped.
- Numerous smaller tweaks!

## Known issues

- Some seeds will spawn you in water or on a tiny island in the middle of
nowhere. If you want a bit more predictability when creating a world, I would
recommend using [World Preview](https://modrinth.com/mod/world-preview).
- World generation is not as fast as before (roughly 30% slower in my
experience) because the new density functions are much more
complex and the generated terrain is "taller".
- There are a lot of mountains and limited flat terrain. This is intentional. If
you want more flat, go for the areas between mountain ranges and stay near the
coastline.
By using these Fabric mods you can counterbalance the slowness:
    - [C2ME](https://modrinth.com/mod/c2me-fabric) (Biggest performance boost, but can be unstable)
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

If WWOO is loaded **after** Larion, which is not easy to predict, mountains
taller than Y=320 will be "chopped" beacuse WWOO overwrites the file that
determines world height. 

By unpacking the WWOO .zip/.jar file and removing **all three** files named
overworld.json, the issue is now gone and load order is no longer important!

Be sure to also use **Cliffs and Coves**  by the same
author, as it can greatly improve the look of coasts and beaches. There is
also Navigable Rivers, but it's not important as Larion does pretty much the
same thing with its river tunnels.

### William Wyther's Expanded Ecosphere

**100% compatible**. WWEE only makes changes to files that Larion
does not touch so they can be loaded together without issues.

These go very well together, biome diversity is significantly better.

There is one caveat, the new "island" biomes that replace mushroom islands are
going to be very small and weird looking. Sorry!

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
    - Several changes inside the giant `final_density` function
        - Modified the `y_clamped_gradient` that previously limited mountain height
          to 240-256 to be at 420-447 (in `final_density` and
        `initial_density_without_jaggedness`
        - Inject the `larion:overworld/river_noodle` and `larion:overworld/caves/lava_river_noodle` density functions to `final_density` to make rivers carve and generate lava rivers.
    - Changed `fluid_level_floodedness` and `fluid_level_spread` using `larion:overworld/river_noodle_flood` to make sure river caves are always "flooded" (full of water at sea level) and also remove most aquifers near lava rivers.
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
- `entrances`, `noodle` and `spaghetti_2d` caves were modified

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

The current version took several months of precious free time to make.
If you like the project and want to send me a donation, here is a Paypal link:

https://www.paypal.com/donate/?hosted_button_id=L2WKHTDJ4DANU

.. or alternatively send some Bitcoin to bc1qk5688pjsy228zkrda5e9w43wzn0zye4w0ygej4
