package org.highresstartup.client;

import java.util.Properties;
import java.util.Locale;
import java.io.File;

final class HighResStartupSettings {
    static String resourcePack =
            "mods/highres-startup/highres-startup-resources-"
                    + HighResStartupMod.VERSION + ".jar";
    static String startupScreen = HighResStartupLibrary.RANDOM;

    private HighResStartupSettings() {
    }

    static void configure(Properties properties) {
        resourcePack = properties.getProperty(
                "resourcePack", "mods/highres-startup/highres-startup-resources-"
                        + HighResStartupMod.VERSION + ".jar").trim();
        String selection = properties.getProperty("startupScreen", HighResStartupLibrary.RANDOM)
                .trim().toLowerCase(Locale.ROOT);
        startupScreen = HighResStartupLibrary.contains(selection) ? selection : HighResStartupLibrary.RANDOM;
    }

    static File resolvePack() {
        File pack = new File(resourcePack);
        if (!pack.isAbsolute()) pack = new File(System.getProperty("user.dir"), resourcePack);
        return pack.getAbsoluteFile();
    }
}
