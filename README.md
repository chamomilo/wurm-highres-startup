<p align="center">
  <img src="assets/icons/highres-startup-logo-128.png" width="128" height="128" alt="HighRes Startup">
</p>

# HighRes Startup

Two loading-screen illustrations for **Wurm Unlimited**, packaged at
**3840 × 2160**. Pick a favourite or let the mod choose a screen at random on
launch. The native loading messages, progress display and reconnect controls
continue to work as usual.

**Version 0.4.1** · **Chamomilo updater 1.5.0**

[Download the installation ZIP](https://github.com/chamomilo/wurm-highres-startup/releases/latest)
· [Report an issue](https://github.com/chamomilo/wurm-highres-startup/issues)

## Screen library

### Wurm Reimagined

A brighter and more detailed version of the familiar Wurm composition: four
adventurers, the castle and water mill, and a giant emerging through the mist.

![Wurm Reimagined loading screen](assets/wurm-reimagined-preview.png)

### Sklotopolis

The Sklotopolis illustration with a party of adventurers, a riverside settlement
and towering gods in the background.

![Sklotopolis loading screen](assets/highres-startup-preview.png)

## Choose a screen

Open **Mod updates**, find **HighRes Startup**, and click **Settings**.
The **Startup screen** selector offers:

| Choice | Behaviour |
| --- | --- |
| `random` | Choose either screen with equal probability on each client launch. This is the default. |
| `wurm-reimagined` | Always show Wurm Reimagined. |
| `sklotopolis` | Always show Sklotopolis. |

Restart the client after changing the selection. Reconnecting keeps the current
screen. Random selection can show the same image on consecutive launches.

## Install or update

Requires **Wurm Unlimited** and
[Ago's Client Mod Launcher](https://github.com/ago1024/WurmClientModLauncher).

1. Close the game and WurmLauncher completely.
2. Download **highres-startup-0.4.1.zip** from Releases.
3. Extract it into the game's **WurmLauncher** directory, merging the included
   `mods` folder.
4. When updating, remove the older `highres-startup-<version>.jar` and
   `highres-startup-resources-<version>.jar` from `mods/highres-startup`.
5. Start the client through the mod launcher.

The installed layout must be:

```text
WurmLauncher/
  mods/
    highres-startup.properties
    highres-startup/
      highres-startup-0.4.1.jar
      highres-startup-resources-0.4.1.jar
```

The ZIP contains those three mod files and **README.txt**. The resource JAR
contains both images and the screen catalogue; the main JAR includes the shared
updater and license. Both JARs are required.

## Shared mod updates

Chamomilo updater **1.5.0** checks participating mods in one shared window after
the game HUD is ready. Open **Mod updates** to return to the registry, check
available releases or change settings. **DOWNLOAD** opens the release page;
download and install updates manually, then restart the client.

The updater selects the newest compatible embedded module among installed
Chamomilo mods. Its language selector supports **English**, **Brazilian
Portuguese**, **German** and **Russian**. Language and startup visibility
preferences are shared between participating mods.

## Troubleshooting

- **The stock screen still appears:** check the installation layout and launch
  the client through the mod launcher.
- **Artwork is missing:** keep both versioned JARs together. The `resourcePack`
  setting must point to the current resource JAR.
- **A screen cannot be loaded:** the mod tries another library image, then
  allows the game's normal fallback. Include the mod version and relevant
  `HighResStartup` log lines in an issue report.

To uninstall, close the client and remove `mods/highres-startup.properties`
and the `mods/highres-startup` directory.

## Build from source

Use **JDK 8**, the Gradle wrapper and the canonical **Chamomilo updater**
reference project. Provide the client libraries read-only: `client-patched.jar`,
`common.jar`, `javassist.jar` and `modlauncher.jar`. The updater reference also
requires its JUnit, Hamcrest and JavaFX libraries, as described in its
integration guide.

```text
gradlew clean test build dist -PwurmClientLibDir=/path/to/client/libraries -PchamomiloUpdaterReference=/path/to/updater
```

Pass `-PwurmJavaFxJar=/path/to/jfxrt.jar` if JavaFX is not bundled with the JDK.
The verified installation ZIP is written to **dist/**. Release checks cover both
images, screen selection, reconnect caching, the startup-renderer hook and exact
updater contents in the mod JAR and ZIP.

## License

Source code is licensed under [GPL-3.0](LICENSE). The main JAR includes the full
license at `META-INF/LICENSE.txt`.

Wurm and Sklotopolis names and branding belong to their respective owners.
This is an unofficial fan-made mod, unaffiliated with Wurm Online,
Wurm Unlimited, Code Club or Sklotopolis.
