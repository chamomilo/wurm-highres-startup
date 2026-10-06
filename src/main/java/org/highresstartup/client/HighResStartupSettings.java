package org.highresstartup.client;

import java.util.Properties;

final class HighResStartupSettings {
    static String resourcePack =
            "mods/highres-startup/highres-startup-resources-0.3.6.jar";

    private HighResStartupSettings() {
    }

    static void configure(Properties properties) {
        resourcePack = properties.getProperty(
                "resourcePack", resourcePack).trim();
    }
}
