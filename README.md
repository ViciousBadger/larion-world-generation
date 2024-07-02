![Banner](images/banner.jpg)

# Larion - Epic Fantasy World Generation for Minecraft 1.19.2-1.20.6

Do you like mountains?

Do you like REALLY TALL mountains?

Do you like terrain so hilly and full of cliffs, basic navigation becomes a
major challenge?

Do you also like sailing the high seas? Exploring far-off islands? Huge inland
valleys? Massive deserts? Long, winding river tunnels? Floating mushroom islands!?!

Then this Fabric mod was practically made for you!

Larion is a major revamp of the overworld generation in Minecraft that aims to
be a blend of **Epic fantasy** (think World of Warcraft, Lord of the Rings,
realism is not a priority here, cool factor is!) and **Minecraftness** (The
janky, hilly, blobby terrain Minecraft is known for that was most prominent in
Alpha and Beta versions).

To accomplish this, Larion makes several major changes to how biomes are
distributed, along with some extra terrain features described below.

## How to install

Larion **requires** the [More Density
Functions](https://modrinth.com/mod/more-density-functions) Fabric mod in order
to function, as several important features make use of the "sine" and "x"
functions added by this mod. (If anyone from Mojang reads this, please add these
functions to the core game! It would be great to be able to distribute Larion
as a pure vanilla data pack.)


- Download [More Density Functions](https://modrinth.com/mod/more-density-functions) and move it to your `.minecraft/mods/` folder
- Download `Larion-xxxx-mcxxxx.jar` and move it to your `.minecraft/mods/` folder.
    - Alternatively, you can download `Larion-xxxx-mcxxxx.zip` and add it
    directly to your world as a datapack, but More Density Functions is still
    required for it to work.

# Features

TODO

# Known issues

- World generation performance is heavily impacted due to the increased
complexity of density functions and increased world height. The extra "jank"
effect that creates overhangs seems to cause the largest reduction in speed
because it adds two extra layers of full-3d noise to the mix. You can remedy
this by using worldgen optimization mods - I recommend these:
    - [C2ME](https://modrinth.com/mod/c2me-fabric) (MAJOR performance boost, scales with CPU core count)
    - [Noisium](https://modrinth.com/mod/noisium)
    - [Faster Random](https://modrinth.com/mod/faster-random)
    - [FerriteCore](https://modrinth.com/mod/ferrite-core)
- There seems to be some kind of incompability between **More Density
Functions** and **Chunky** - in my experience, Chunky fails to generate any new
chunks, which is unfortunate.. I experienced no such problems with the other
chunk pre-generation methods I tried:
    - [Distant Horizons](https://modrinth.com/mod/distanthorizons) (Only generates LODs, not real chunks)
    - [Fabric/Quilt Chunk Pregenerator](https://modrinth.com/mod/distanthorizons) (Not as featureful as Chunky, but a working alternative for now)

# Note on datapack compability

Larion overwrites a lot of world generation data files. Unfortunately this can
cause compability issues with several other datapacks that change the same
files. To make it easier to create compability patches etc, here is a list of
overwritten files along with descriptions what has been changed.

Note that Larion does NOT change any of the biome files, so mods that only
modify biomes (such as Arboria and Geophilic) should be fully compatible!

[William Wyther's Overhauled Overworld](https://modrinth.com/mod/wwoo) is also
fully compatible (and heavily recommended!!) as long as you delete
`data/minecraft/dimension_type/overworld.json` from its .zip/.jar archive -
otherwise tall mountains will be cut off at Y=320. I have no idea why WWOO
overwrites this file, it seems no actual changes were made..

## Changes to vanilla data files

- `dimension_type/overworld.json` and `dimension_type/overworld_caves.json`
    - Changed `height` and `logical_height` to 512
- `worldgen/noise_settings/overworld.json`
    - Changed `noise/height` to 512
    - Two changes inside the giant `final_density` function
        - Modified the `y_clamped_gradient` that previously limited mountain height
          to 240-256 to be at 420-447 instead
        - Inject the `larion:overworld/river_noodle` density function inside/near `blend_density`
    - Changed `fluid_level_floodedness` and `fluid_level_spread` using `larion:overworld/river_noodle_flood` to make sure river caves are always "flooded" (full of water at sea level)
    - Changed `temperature` and `vegetation` to point at my own density
    functions instead of just being simple noises
- Six density functions in `overworld` have been overwritten
    - `continents`, `erosion` and `ridges` have all been rewired to point at
    density functions in the `larion` scope
    - `depth` is edited to increase max terrain height to 448 (from 320)
    - `offset` has been modified to make sure the sea level aligns correctly
    (The weirdly specific `-0.7437500262260437` number) and also to add
    `larion:overworld/dunes` to terrain height
    - `sloped_cheese` has been modified to add `larion:overworld/minecraftness`
    to overall terrain density, creating overhangs and weirdness
