package org.highresstartup.client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class HighResStartupResources {
    private static final Logger LOG = Logger.getLogger("HighResStartup");
    private static boolean attempted;
    private static BufferedImage startupImage;

    private HighResStartupResources() {
    }

    public static synchronized BufferedImage loadStartupImage() {
        if (startupImage != null) return startupImage;
        if (attempted) return null;
        attempted = true;

        startupImage = loadFromPack(HighResStartupSettings.resolvePack(),
                HighResStartupSettings.startupScreen, new Random());
        return startupImage;
    }

    /** Loads only one full-resolution image; failed candidates never prevent a fallback. */
    static BufferedImage loadFromPack(File pack, String selection, Random random) {
        if (!pack.isFile()) {
            LOG.severe("Startup-screen image archive is missing: " + pack);
            return null;
        }

        try (ZipFile archive = new ZipFile(pack)) {
            List<HighResStartupLibrary.Screen> candidates = candidates(selection, random);
            for (HighResStartupLibrary.Screen screen : candidates) {
                ZipEntry entry = archive.getEntry(screen.imageEntry);
                if (entry == null) {
                    LOG.warning("Startup image is missing: " + screen.imageEntry + " in " + pack);
                    continue;
                }
                try (InputStream input = archive.getInputStream(entry)) {
                    BufferedImage image = ImageIO.read(input);
                    if (image == null || image.getWidth() != 3840 || image.getHeight() != 2160) {
                        LOG.warning("Startup image is unreadable or not 3840x2160: " + screen.imageEntry);
                        continue;
                    }
                    LOG.info("Loaded startup screen '" + screen.title + "' (" + screen.id
                            + "): " + image.getWidth() + "x" + image.getHeight() + " from " + pack);
                    return image;
                } catch (IOException error) {
                    LOG.log(Level.WARNING, "Unable to read startup screen: " + screen.imageEntry, error);
                }
            }
            LOG.severe("No usable startup screens in " + pack + "; using the stock screen");
            return null;
        } catch (IOException error) {
            LOG.log(Level.SEVERE,
                    "Unable to read startup image directly from " + pack,
                    error);
            return null;
        }
    }

    static List<HighResStartupLibrary.Screen> candidates(String selection, Random random) {
        List<HighResStartupLibrary.Screen> result =
                new ArrayList<HighResStartupLibrary.Screen>(HighResStartupLibrary.SCREENS);
        Collections.shuffle(result, random);
        if (!HighResStartupLibrary.RANDOM.equals(selection)) {
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).id.equals(selection)) {
                    result.add(0, result.remove(i));
                    break;
                }
            }
        }
        return result;
    }
}
