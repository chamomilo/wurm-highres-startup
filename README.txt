HIGHRES STARTUP 0.4.1
Loading-screen library for Wurm Unlimited
Chamomilo shared updater 1.5.0

Includes Wurm Reimagined and Sklotopolis, both packaged at 3840 x 2160.
Preserves the game's native loading messages, progress and reconnect controls.

INSTALL OR UPDATE
Requires Ago's Wurm Unlimited Client Mod Launcher:
https://github.com/ago1024/WurmClientModLauncher

1. Close the game and WurmLauncher completely.
2. Extract this ZIP into the game's WurmLauncher directory, merging mods.
3. Confirm the installed layout:
   mods/highres-startup.properties
   mods/highres-startup/highres-startup-0.4.1.jar
   mods/highres-startup/highres-startup-resources-0.4.1.jar
4. When updating, remove the older highres-startup-<version>.jar and
   highres-startup-resources-<version>.jar from mods/highres-startup.
5. Start the client through the mod launcher.

Both JARs are required. The ZIP contains these three mod files and README.txt.

CHOOSE A SCREEN
Open Mod updates, find HighRes Startup, and click Settings.
Use the Startup screen selector:
  random           Choose either screen equally on each launch (default).
  wurm-reimagined   Always show the reimagined standard Wurm screen.
  sklotopolis      Always show the Sklotopolis illustration.

Restart the client after changing the selection. Reconnecting keeps the current
screen. Consecutive random selections can show the same image.

SHARED MOD UPDATES
Chamomilo updater 1.5.0 checks participating mods in one shared window after
the game HUD is ready. Open Mod updates to return to the registry.
DOWNLOAD opens the release page. Download and install updates manually,
then restart the client.

The newest compatible embedded updater is selected among installed mods.
Languages: English, Brazilian Portuguese, German and Russian.
Language and startup visibility preferences are shared by participating mods.

TROUBLESHOOTING
If the stock screen still appears, check the installed layout and launch
through the mod launcher. Keep both versioned JARs together. The resourcePack
setting must point to the current resource JAR.

If an image cannot be loaded, another library image is tried, then the game's
normal fallback. Include the mod version and relevant HighResStartup log lines
when reporting an issue.

UNINSTALL
Close the client. Remove mods/highres-startup.properties and the
mods/highres-startup directory.

ILLUSTRATED GUIDE, DOWNLOADS AND SOURCE
https://github.com/chamomilo/wurm-highres-startup
https://github.com/chamomilo/wurm-highres-startup/releases/latest

REPORT AN ISSUE
https://github.com/chamomilo/wurm-highres-startup/issues

LICENSE
Source code: GPL-3.0. The full license is inside highres-startup-0.4.1.jar
at META-INF/LICENSE.txt and in the source repository.
Wurm and Sklotopolis names and branding belong to their respective owners.
This unofficial fan-made mod is unaffiliated with Wurm Online,
Wurm Unlimited, Code Club or Sklotopolis.
