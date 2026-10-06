# High-resolution Sklotopolis startup screen 0.3.6

Minimal client-side mod for Wurm Unlimited that replaces the stock connecting
screen with a 3840x2160 Sklotopolis image.

## Download and installation

[Download the ready-to-install ZIP from GitHub Releases.](https://github.com/chamomilo/wurm-highres-startup/releases/download/v0.3.6/highres-startup-0.3.6.zip)

1. Install Ago's Wurm Unlimited Client Mod Launcher.
2. Extract the ZIP into the Wurm Unlimited `WurmLauncher` directory and merge
   the included `mods` directory.
3. Fully close WurmLauncher, then start the modded client.

## Repository contents

The repository contains the mod source, build wrapper, configuration and its
3840x2160 startup artwork. Ready-to-install binaries are attached to Releases;
generated JARs, local client libraries and artwork experiments are excluded.

Build with JDK 8 and the local Wurm client libraries:

```text
gradlew clean test build dist -PwurmClientLibDir=/path/to/client/libraries
```

This is an unofficial fan-made client mod and is not affiliated with or endorsed
by Wurm Online, Wurm Unlimited, Code Club, or Sklotopolis.

## Chamomilo versions

Version 0.3.6 embeds the shared Chamomilo updater. A versions window opens at every launch after the HUD is ready and lists all mods from the public GitHub catalogue, including disabled and absent installations. UPDATE opens a newer installed release; INSTALL opens a release for an absent mod. ZIP installation remains manual. The public catalogue is refreshed without requiring new client binaries; a verified copy is retained for offline startup. All Chamomilo updater copies share one window, with a thin high-resolution wood-and-metal frame.
