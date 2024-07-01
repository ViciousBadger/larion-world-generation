# Larion


Datapack for Minecraft 1.20.4 that drastically alters world generation. The goal
was a mix of epic fantasy terrain and old alpha Minecraft weirdness.

## Requirements

Larion **requires** the
[More Density Functions](https://modrinth.com/mod/more-density-functions)
Fabric mod in order to function, as several important features make use of the
"sine" and "x" functions added by this mod.

# Changes to vanilla data files

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
