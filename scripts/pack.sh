#!/usr/bin/env sh
# Just a quick script used to distribute the mod
7z a "$1/Larion-1.0.0-mc1.19.2-to-1.20.6.jar" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png LICENSE.md
# Zip extension = datapack (Mojang made it so jar files cannot be used as datapacks.. for some reason..)
7z a "$1/Larion-1.0.0-mc1.19.2-to-1.20.6.zip" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png LICENSE.md

echo "packed to $1"
