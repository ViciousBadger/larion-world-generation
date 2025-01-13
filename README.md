![Larion project logo](images/larion_logo.png)

# Larion World Generation, for Minecraft 1.20-1.21.1

Larion is a terrain generation overhaul that creates worlds inspired by epic fantasy media, particularily games like Elden Ring and Skyrim.

You will find yourself travelling rolling hills, twisting river valleys, large lakes, treacherous mountain ranges, strange eroded areas of swamps and clefts, all surrounded by an infinte sea of continents and island groups. The scale is overall larger than vanilla and terrain is a bit more realistically shaped, but still "video-gamey" and compact. There is a strong focus on generating steep cliffs and other "terrain hazards" that make the world more interesting to explore and build in.

Larion alters the **shape of the terrain**, the **layout of biomes** and a few **surface rules**, (stone instead of dirt/grass on all steep slopes and mud in rivers), but makes zero changes to the actual biomes, allowing you to pair it with any compatible biome-altering mods or just keep it close to vanilla as the screenshots show.

If you want a limited world size, check out the [Disc
World](https://modrinth.com/datapack/larion-one-continent) add-on datapack!

Please note that the mod is still in active development, future updates are nearly guaranteed to change how the world is generated. I would suggest not updating the mod on existing worlds unless your are okay with weird chunk borders.

## Recommended mods/datapacks

I can heavily recommend playing with [Distant Horizons](https://modrinth.com/mod/distanthorizons) and pre-generating your world to allow the scale of the terrain to strike awe from the get-go.

I also recommend using [William Wyther's Overhauled Overworld](https://modrinth.com/datapack/william-wythers-overhauled-overworld-(datapack)) as a complimentary worldgen datapack as it is frankly a work of art and its visual style fits Larion perfectly. Be sure to use the datapack version, if you use WWOO as a mod you may run into issues with load order.

Some seeds will spawn you in water or on a tiny island in the middle of
nowhere. If you want a bit more predictability when creating a world, I would
recommend using [World Preview](https://modrinth.com/mod/world-preview).

Finally, you should stock up on worldgen optimization mods, as Larion tends to slow it down a lot due to the added complexity. Here are a few:
- [C2ME](https://modrinth.com/mod/c2me-fabric) (Biggest performance boost, but can be unstable)
- [Noisium](https://modrinth.com/mod/noisium)
- [Faster Random](https://modrinth.com/mod/faster-random)
- [FerriteCore](https://modrinth.com/mod/ferrite-core)

## Compability notes with other worldgen datapacks/mods

As a rule of thumb, if mods or datapacks modify Minecraft's **density functions** (in worldgen/density_functions), there will be compability issues with Larion and your worlds will probably look weird in one way or the other. Basically any mod that alters terrain shape or caves, for example Terralith, Tectonic, Litosphere, Big Globe, Expanded Echosphere and Cascades.

However, mods and datapacks that only alter biome features and/or add new biomes should be fully compatible.

## Changes to vanilla data files

To make it easier to create compability patches etc, here is a list of
overwritten files along with descriptions what has been changed.

- `dimension_type/overworld.json` and `dimension_type/overworld_caves.json`
    - Changed `height` and `logical_height`
- `worldgen/noise_settings/overworld.json`
    - Changed `noise/height`
    - Several changes inside the giant `final_density` function
        - Modified the `y_clamped_gradient` sections in `final_density` and `initial_density_without_jaggedness`
        - Injected the `larion:overworld/river_noodle` and `larion:overworld/caves/lava_river_noodle` density functions to `final_density` to carve out underground rivers.
    - Changed `fluid_level_floodedness` and `fluid_level_spread` using `larion:overworld/river_noodle_flood` to make sure river caves are always "flooded" (full of water at sea level) and also remove most aquifers near lava rivers.
    - Changed `temperature` and `vegetation` to point at larion's custom density functions
- Various functions in `worldgen/density_functions/overworld` have been overwritten:
    - `continents`
    - `erosion`
    - `ridges`
    - `depth`
    - `factor`
    - `sloped_cheese`
    - In `caves/`:
        - `entrances`
        - `noodle`
        - `spaghetti_2d`

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

If you like the project and want to send me a personal donation, here is a Paypal link:

https://www.paypal.com/donate/?hosted_button_id=L2WKHTDJ4DANU

.. or alternatively send some Bitcoin to bc1qk5688pjsy228zkrda5e9w43wzn0zye4w0ygej4
