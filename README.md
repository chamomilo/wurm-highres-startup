<p align="center">
  <img src="assets/icons/highres-startup-logo-128.png" width="128" height="128" alt="HighRes Startup logo">
</p>

# HighRes Startup

A 4K Sklotopolis startup screen for **Wurm Unlimited**. Replaces the stock
connecting screen with a **3840 × 2160** illustration, keeping the game's native
startup layout and progress display.

![Sklotopolis startup artwork included in the mod](assets/highres-startup-preview.png)

**Current version: 0.3.9** · **Shared Chamomilo updater: 1.4.1**

[Download the installation ZIP](https://github.com/chamomilo/wurm-highres-startup/releases/latest)
· [Report an issue](https://github.com/chamomilo/wurm-highres-startup/issues)

## Installation

Requires Wurm Unlimited with
[Ago's Client Mod Launcher](https://github.com/ago1024/WurmClientModLauncher).

1. Close Wurm Unlimited and WurmLauncher completely.
2. Download `highres-startup-0.3.9.zip` from Releases.
3. Extract it into the game's **WurmLauncher** directory, merging the included
   `mods` folder. Confirm that `highres-startup.properties` is directly inside
   `WurmLauncher/mods`, without an extra enclosing folder.
4. When updating an older installation, remove its old
   `highres-startup-<old-version>.jar` and
   `highres-startup-resources-<old-version>.jar` from `mods/highres-startup`.
5. Start the modded client. The illustration appears on the connecting screen.

The ZIP contains exactly these four files:

```text
README.txt
mods/
  highres-startup.properties
  highres-startup/
    highres-startup-0.3.9.jar
    highres-startup-resources-0.3.9.jar
```

The resource JAR contains the full-resolution artwork. README images are kept in
this repository; they are not extra installation files.

## Mod updates

The embedded Chamomilo updater checks participating mods in one shared window
after the game HUD is ready. Open it again through the native **Mod updates**
menu. Its startup visibility preference is shared between Chamomilo mods.

**DOWNLOAD** opens a release page. Download and install the ZIP manually, then
restart the client. The updater does not install files into the game.

Updater 1.4.1 selects the newest compatible embedded module across active
Chamomilo mods before their initialization. Its shared interface and language
selector support English, Brazilian Portuguese, German and Russian.

## Troubleshooting and removal

- **Stock image still visible:** check the directory layout above, ensure the
  client is launched through the mod launcher, and restart it completely.
- **Missing artwork:** keep both versioned JARs together in `mods/highres-startup`.
  The `resourcePack` setting in the descriptor must point to the resource JAR.
- **Loading failure:** include the mod version and relevant `HighResStartup`
  lines from the client log when reporting an issue. If the image cannot be
  read, the mod falls back to the stock startup image.

To uninstall, close the client and remove `mods/highres-startup.properties` and
the `mods/highres-startup` directory.

## Building from source

Use **JDK 8**, the bundled Gradle wrapper, and the canonical Chamomilo updater
reference checkout. Provide read-only client libraries: `client-patched.jar`,
`common.jar`, `javassist.jar` and `modlauncher.jar`. The updater reference also
needs its JUnit and Hamcrest test libraries, as described in its integration guide.
If the JDK does not bundle JavaFX, pass `-PwurmJavaFxJar=/path/to/jfxrt.jar`
for the updater's native interface checks.

```text
gradlew clean test build dist -PwurmClientLibDir=/path/to/client/libraries -PchamomiloUpdaterReference=/path/to/updater
```

The build verifies the 4K artwork, the direct startup-renderer hook, the early
updater bootstrap, and exact embedded updater bytes in both the mod JAR and ZIP.
The installation ZIP is written to `build/distributions`.

## License

Source code is licensed under [GPL-3.0](LICENSE). The installation JAR includes
the full license at `META-INF/LICENSE.txt`. Wurm and Sklotopolis names and
branding belong to their respective owners.

This is an unofficial fan-made client mod, unaffiliated with Wurm Online,
Wurm Unlimited, Code Club or Sklotopolis.
