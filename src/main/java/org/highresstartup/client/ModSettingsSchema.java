package org.highresstartup.client;

import org.chamomilo.wurm.update.ModSetting;
import java.util.*;

/** Product metadata for the shared native Settings editor. */
final class ModSettingsSchema {
    private ModSettingsSchema() { }
    static List<ModSetting> specs() {
        return Arrays.asList(
            ModSetting.choice("Screen library", "startupScreen", "Startup screen",
                    "random chooses equally between all library screens on each client launch. "
                            + "sklotopolis keeps Sklotopolis; wurm-reimagined keeps Wurm Reimagined. "
                            + "The selected image stays the same on reconnect. Applies after restarting the client.",
                    HighResStartupLibrary.RANDOM, false, HighResStartupLibrary.choices()),
            ModSetting.text("Resources", "resourcePack", "Startup image resource pack",
                    "Resource pack containing the complete 4K screen library. Applies after restarting the client.",
                    "mods/highres-startup/highres-startup-resources-" + HighResStartupMod.VERSION + ".jar", false)
        );
    }
}
