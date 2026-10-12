<p align="center">
  <img src="assets/icons/highres-startup-logo-128.png" width="128" height="128" alt="HighRes Startup logo">
</p>

# HighRes Startup

A **4K loading-screen library for Wurm Unlimited**. Choose Wurm Reimagined or
Sklotopolis, or let the mod pick either illustration at random on each launch.
The game's loading text, progress display and reconnect controls remain available.

**0.4.3** · **3840 × 2160 artwork** · **Chamomilo updater 1.5.0 with Settings**

[Download the latest ZIP](https://github.com/chamomilo/wurm-highres-startup/releases/latest)
· [Release notes](CHANGELOG.md)
· [Report an issue](https://github.com/chamomilo/wurm-highres-startup/issues)

## Included screens

### Wurm Reimagined

Adventurers, a water mill and a mountain castle beneath the familiar Wurm logo,
with a giant appearing through the mist.

![Wurm Reimagined loading-screen preview](assets/wurm-reimagined-preview.png)

### Sklotopolis

A riverside settlement and a party of adventurers beneath the Sklotopolis banner,
with towering gods in the background.

![Sklotopolis loading-screen preview](assets/highres-startup-preview.png)

Both images are included at **3840 × 2160**. The previews above are smaller
copies for this guide.

## Install or update

Requires **Wurm Unlimited** and
[Ago's Client Mod Launcher](https://github.com/ago1024/WurmClientModLauncher).

1. Close Wurm Unlimited and WurmLauncher completely.
2. Download **highres-startup-0.4.3.zip** from
   [Releases](https://github.com/chamomilo/wurm-highres-startup/releases/latest).
3. If updating, remove the older `highres-startup-<version>.jar` and
   `highres-startup-resources-<version>.jar` from `mods/highres-startup`.
4. Extract the ZIP into **WurmLauncher**, merging `mods` and replacing
   `mods/highres-startup.properties`.
5. Start the game through the mod launcher.

The installed mod must have this layout:

```text
WurmLauncher/
  mods/
    highres-startup.properties
    highres-startup/
      highres-startup-0.4.3.jar
      highres-startup-resources-0.4.3.jar
```

**Both JARs are required.** The main JAR contains the mod, shared updater and
license; the resource JAR contains the screen library. The ZIP contains these
three mod files and **README.txt**.

## Settings

Open **Mod updates → HighRes Startup → Settings** after entering the game.

| Setting | What it does |
| --- | --- |
| **Startup screen** | `random` selects either illustration with equal probability on each launch; `wurm-reimagined` or `sklotopolis` keeps a fixed screen. Default: `random`. |
| **Startup image resource pack** | Path to the JAR containing both images. Default: `mods/highres-startup/highres-startup-resources-0.4.3.jar`. |

The editor saves valid changes automatically and provides hover help.
**Both settings apply after restarting the client.** A saved change shows a
restart reminder; the exit action opens Wurm's normal quit confirmation.

Reconnecting preserves the current image. Random selection can choose the same
screen on consecutive launches.

Your explicit overrides are stored in
`~/.chamomilo/mod-settings/highres-startup.properties`, outside the installation.
On Windows, `~` means your user folder. An unchanged resource-pack setting follows
the new version when you update; if you explicitly saved a custom path, update
that path when replacing its JAR.

## Shared mod updates

The embedded **Chamomilo updater 1.5.0** checks participating mods together once
the game HUD is ready. Use **Mod updates** to check releases and open each
installed mod's **Settings**. **DOWNLOAD** opens the release page for manual
download and installation.

The shared language selector supports **English**, **Brazilian Portuguese**,
**German** and **Russian**. Language and startup visibility preferences are
shared between participating Chamomilo mods. When several mods embed the updater,
the newest compatible module is selected.

## Troubleshooting

| Symptom | Check |
| --- | --- |
| The standard loading screen appears | Confirm the layout above and start through the mod launcher. |
| An image cannot be loaded | Keep both JARs installed and check the resource-pack path in Settings. The mod tries the other library image before allowing the game's normal fallback. |
| A new selection does not appear | Restart the client fully; reconnecting keeps the current screen. |
| The client stopped launching after installing 0.4.1 | Install 0.4.3, which includes the catalogue-loading fix verified with Wurm's shared loader. |

For an issue report, include the installed mod version and relevant
`HighResStartup` lines from `WurmLauncher/client.log`.

To uninstall, close the client, then remove `mods/highres-startup.properties`
and `mods/highres-startup`.

## Build from source

Use **JDK 8**, the Gradle wrapper and the canonical Chamomilo updater reference.
Provide `client-patched.jar`, `common.jar`, `javassist.jar`, `modlauncher.jar`,
`junit-4.13.2.jar` and `hamcrest-core-1.3.jar` in a client-library directory.

```text
gradlew clean test build dist -PwurmClientLibDir=/path/to/libraries -PchamomiloUpdaterReference=/path/to/updater -PwurmJavaFxJar=/path/to/jfxrt.jar
```

The installable ZIP is written to **dist/**. Release checks cover artwork,
selection, fallback, reconnect, packaged startup and saved Settings through Wurm's
actual loader. The build also verifies the complete canonical updater's entry
names and bytes in the mod JAR and ZIP.

## License

Source code is licensed under [GPL-3.0](LICENSE). The complete license is included
in the main JAR at `META-INF/LICENSE.txt`.

Wurm and Sklotopolis names and branding belong to their respective owners.
This is an unofficial fan-made mod, unaffiliated with Wurm Online,
Wurm Unlimited, Code Club or Sklotopolis.
