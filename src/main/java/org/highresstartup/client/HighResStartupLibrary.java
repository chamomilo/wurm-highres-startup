package org.highresstartup.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/** Stable screen identities and artwork paths shared by settings and loading. */
final class HighResStartupLibrary {
    static final String RANDOM = "random";
    static final List<Screen> SCREENS = readCatalogue();

    private HighResStartupLibrary() { }

    static String[] choices() {
        String[] choices = new String[SCREENS.size() + 1];
        choices[0] = RANDOM;
        for (int i = 0; i < SCREENS.size(); i++) choices[i + 1] = SCREENS.get(i).id;
        return choices;
    }

    static boolean contains(String id) {
        for (Screen screen : SCREENS) if (screen.id.equals(id)) return true;
        return false;
    }

    private static List<Screen> readCatalogue() {
        Properties properties = new Properties();
        try (InputStream input = openCatalogue()) {
            if (input == null) throw new IOException("Missing startup-screen catalogue");
            properties.load(input);
        } catch (IOException error) {
            throw new IllegalStateException("Cannot read startup-screen catalogue", error);
        }
        List<Screen> screens = new ArrayList<Screen>();
        for (String value : properties.getProperty("screens", "").split(",")) {
            String id = value.trim();
            String title = properties.getProperty(id + ".title", "").trim();
            String path = properties.getProperty(id + ".image", "").trim();
            if (!id.matches("[a-z][a-z0-9-]*") || RANDOM.equals(id)
                    || title.isEmpty() || !path.matches("startup/[a-z0-9-]+\\.png")) {
                throw new IllegalStateException("Invalid startup-screen catalogue entry: " + id);
            }
            for (Screen existing : screens) {
                if (existing.id.equals(id) || existing.imageEntry.equals(path)) {
                    throw new IllegalStateException("Duplicate startup-screen catalogue entry: " + id);
                }
            }
            screens.add(new Screen(id, title, path));
        }
        return Collections.unmodifiableList(screens);
    }

    private static InputStream openCatalogue() throws IOException {
        InputStream input = HighResStartupLibrary.class.getResourceAsStream("screens.properties");
        if (input != null) return input;
        // Wurm's shared Javassist loader loads classes from mod JARs, but does
        // not expose their resources. Resolve beside this class in its active JAR.
        URL classFile = org.gotti.wurmunlimited.modloader.classhooks.HookManager
                .getInstance().getClassPool().find(HighResStartupLibrary.class.getName());
        if (classFile == null) throw new IOException("Cannot locate startup-screen catalogue JAR");
        return new URL(classFile, "screens.properties").openStream();
    }

    static final class Screen {
        final String id, title, imageEntry;

        Screen(String id, String title, String imageEntry) {
            this.id = id;
            this.title = title;
            this.imageEntry = imageEntry;
        }
    }
}
