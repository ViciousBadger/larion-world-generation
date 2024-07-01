#!/usr/bin/env sh
7z a "$1/Larion-1.0.0-mc1.19.2-to-1.20.6.jar" data 1-20-5-overlay fabric.mod.json pack.mcmeta pack.png
echo "packed to $1"
