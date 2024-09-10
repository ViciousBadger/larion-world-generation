#!/usr/bin/env sh
filename='Larion-3.0.1-mc1.19.2-to-1.21.1'
# Just a quick script used to distribute the mod
7z a "$1/${filename}.jar" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png LICENSE.md
# Zip extension = datapack (Mojang made it so jar files cannot be used as datapacks.. for some reason..)
7z a "$1/${filename}.zip" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png LICENSE.md

echo "packed to $1"
