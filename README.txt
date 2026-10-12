HIGHRES STARTUP 0.4.3
4K loading-screen library for Wurm Unlimited
Includes Chamomilo updater 1.5.0 with Settings

SCREENS
Wurm Reimagined: adventurers, a water mill, a castle and a giant in the mist.
Sklotopolis: a riverside settlement, a party of adventurers and towering gods.
Both images are included at 3840 x 2160. Native loading text, progress and
reconnect controls remain available.

INSTALL OR UPDATE
Requires Wurm Unlimited and Ago's Client Mod Launcher:
https://github.com/ago1024/WurmClientModLauncher

1. Close Wurm Unlimited and WurmLauncher completely.
2. If updating, remove the older highres-startup-<version>.jar and
   highres-startup-resources-<version>.jar from mods/highres-startup.
3. Extract this ZIP into WurmLauncher, merging mods and replacing
   mods/highres-startup.properties.
4. Confirm the installed layout:
   mods/highres-startup.properties
   mods/highres-startup/highres-startup-0.4.3.jar
   mods/highres-startup/highres-startup-resources-0.4.3.jar
5. Start the game through the mod launcher.

Both JARs are required. This ZIP contains only these three mod files and
README.txt. The main JAR includes the mod, shared updater and license.
The resource JAR contains the complete screen library.

SETTINGS
After entering the game, open Mod updates -> HighRes Startup -> Settings.

Startup screen:
  random           Select either screen with equal probability on each launch.
                   This is the default; consecutive launches may repeat a screen.
  wurm-reimagined   Always show Wurm Reimagined.
  sklotopolis      Always show Sklotopolis.

Startup image resource pack:
  Default: mods/highres-startup/highres-startup-resources-0.4.3.jar
  Change this only when using a different resource-pack location.

Valid changes are saved automatically. Hover over a setting for help.
Both settings require a full client restart; reconnecting retains the current
image. A saved change shows a restart reminder and offers Wurm's standard quit
confirmation. The client never exits automatically.

Explicit overrides are saved outside the installation at:
  ~/.chamomilo/mod-settings/highres-startup.properties
On Windows, ~ means your user folder. An unchanged resource-pack setting follows
new releases. If you explicitly saved a custom path, update it when replacing
that resource JAR.

SHARED MOD UPDATES
Chamomilo updater 1.5.0 checks participating mods together after the HUD is ready.
Open Mod updates to check releases and open installed mods' Settings.
DOWNLOAD opens the release page. Download and install updates manually.

The newest compatible embedded updater is selected among installed mods.
Languages: English, Brazilian Portuguese, German and Russian.
Language and startup visibility preferences are shared by participating mods.

TROUBLESHOOTING
Standard screen: check the installation layout and use the mod launcher.
Missing image: keep both JARs and check the resource-pack path in Settings.
Unchanged selection: restart fully; reconnecting keeps the current screen.
Startup crash with 0.4.1: this release includes the catalogue-loading fix.

An unavailable image falls back to the other library screen, then the game's
normal fallback. Include the mod version and relevant HighResStartup lines from
WurmLauncher/client.log when reporting an issue.

UNINSTALL
Close the client. Remove mods/highres-startup.properties and the
mods/highres-startup directory.

ILLUSTRATED GUIDE AND DOWNLOADS
https://github.com/chamomilo/wurm-highres-startup
https://github.com/chamomilo/wurm-highres-startup/releases/latest

REPORT AN ISSUE
https://github.com/chamomilo/wurm-highres-startup/issues

LICENSE
Source code: GPL-3.0. The full license is included at META-INF/LICENSE.txt
inside highres-startup-0.4.3.jar and in the source repository.
Wurm and Sklotopolis names and branding belong to their respective owners.
This unofficial fan-made mod is unaffiliated with Wurm Online,
Wurm Unlimited, Code Club or Sklotopolis.
