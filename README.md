# High-resolution Sklotopolis startup screen 0.3.4

![Sklotopolis startup-screen preview](assets/highres-startup-preview.png)

Client-side Wurm Unlimited mod that replaces the stock 1920x1080 startup JPEG
with a crisp 3840x2160 PNG. The screen uses a new, more saturated Wurm-inspired
scene with nine distinct adventurers, a waterfall settlement, bridge, mountains,
a female druid with her hell horse, and the sky-reaching Fo, Magranon, Vynora,
and Libila avatars in procession. Their silhouettes, horns, wings, tail, scythe,
armor, colors, and other attributes come from exact renders of the actual Wurm
deity-avatar models, followed by a restrained enrichment pass for richer material
detail and more majestic presentation. The exact `SKLOTOPOLIS` wordmark is the central
visual identity, while a compact free-standing mark on the left identifies the
underlying `WURM UNLIMITED` server without a backing plaque.

The Wurm client scales the 4K master down while preserving its 16:9 aspect ratio,
so it remains sharp on 1920x1080 displays and is ready for 4K displays. No server
mod is required.

## Installation

Requires Ago's Wurm Unlimited Client Mod Launcher.

1. Extract the distribution into `WurmLauncher` and merge its `mods` directory.
2. Fully close any running WurmLauncher process, then start the modded client.

Version 0.3.4 bypasses the game's late-initialized resource-pack manager and
feeds the 4K PNG directly into `StartupRenderer`, so neither pack ordering nor
the stock resource cache can preserve the old connecting-screen image.

## Build and artwork

Run `gradle dist`; the installable archive is written to
`build/distributions/highres-startup-0.3.4.zip`.

`tools/compose-startup.ps1` reproducibly assembles the final 3840x2160 image and
its 1920x1080 preview from the retained source layers. The Sklotopolis reference
wordmark came from the locally installed Wurm Waypointer integration supplied by
the server community. The remastered raster layers were produced with Codex's
built-in ImageGen workflow; the exact prompts are retained under `assets/prompts`.

The exact deity-avatar models and textures were rendered from a locally
installed copy of the game solely as visual references. Those extracted files
are deliberately excluded from this repository. The final background received
a restrained enrichment pass while retaining the defining Fo, Magranon, Vynora,
and Libila silhouettes.

## License and status

The Java, Gradle, and PowerShell source code is available under the MIT terms in
`LICENSE-CODE`. Artwork and third-party names, logos, and trademarks are not
covered by that license; see `NOTICE.md`.

This is an unofficial fan-made client mod and is not affiliated with or endorsed
by Wurm Online, Wurm Unlimited, Code Club, or Sklotopolis.
