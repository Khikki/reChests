# Re:Chests

![Minecraft 1.7.10](https://img.shields.io/badge/Minecraft-1.7.10-3C8527?style=flat-square)
![Forge 10.13.4.1614](https://img.shields.io/badge/Forge-10.13.4.1614-D87F33?style=flat-square)
![License MIT](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

Re:Chests is a vanilla-friendly Minecraft Forge 1.7.10 mod that adds wood-specific chest variants, trapped chests, and chest minecarts.

## Overview

Vanilla Minecraft gives you different wood families for doors, trapdoors, fences, signs, and more, but storage is still mostly stuck with the same chest.

Re:Chests fills that gap by adding chest variants that match your builds without drifting away from the classic Minecraft look.

## Features

- Oak, spruce, birch, jungle, acacia, and dark oak chest variants
- Matching trapped chest variants for every wood family
- Matching chest minecarts for regular chest variants
- Custom single and double chest textures
- Vanilla-friendly crafting and behavior
- Recipe compatibility for common chest-based vanilla uses

## Included Content

### Regular Chests

- Oak Chest
- Spruce Chest
- Birch Chest
- Jungle Chest
- Acacia Chest
- Dark Oak Chest

### Trapped Chests

- Trapped Oak Chest
- Trapped Spruce Chest
- Trapped Birch Chest
- Trapped Jungle Chest
- Trapped Acacia Chest
- Trapped Dark Oak Chest

### Chest Minecarts

- Oak Chest Minecart
- Spruce Chest Minecart
- Birch Chest Minecart
- Jungle Chest Minecart
- Acacia Chest Minecart
- Dark Oak Chest Minecart

## Compatibility

- Minecraft: `1.7.10`
- Forge: `10.13.4.1614`
- Java: `8`

The mod stays close to vanilla chest behavior, including regular and trapped variants, while preventing different wood types from merging into broken double chests.

## Development

This repository also contains small helper scripts used during development:

- `tools/generate_chest_variants.py` for generating chest textures
- `tools/render_block_icons.py` for quick block icon previews
- `tools/render_chest_icons.py` for quick chest previews

## Build

```powershell
.\gradlew.bat build
```

The built jars are written to `build/libs/`.
