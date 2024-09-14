#!/usr/bin/env sh
# Just a quick script used to distribute the mod
filename='Larion-3.2.0'
7z a "$1/${filename}.jar" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png LICENSE.md
# Zip extension = datapack (Mojang made it so jar files cannot be used as datapacks.. for some reason..)
7z a "$1/${filename}.zip" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png LICENSE.md

echo "packed to $1"
