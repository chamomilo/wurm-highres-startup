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

public final class HighResStartupResources {
    private static final Logger LOG = Logger.getLogger("HighResStartup");
    private static final String IMAGE_ENTRY =
            "startup/highres-startupscreen.png";
    private static boolean attempted;
    private static BufferedImage startupImage;

    private HighResStartupResources() {
    }

    public static synchronized BufferedImage loadStartupImage() {
        if (startupImage != null) return startupImage;
        if (attempted) return null;
        attempted = true;

        File pack = resolvePack();
        if (!pack.isFile()) {
            LOG.severe("Startup-screen image archive is missing: " + pack);
            return null;
        }

        try (ZipFile archive = new ZipFile(pack)) {
            ZipEntry entry = archive.getEntry(IMAGE_ENTRY);
            if (entry == null) {
                LOG.severe("Startup image is missing from archive: "
                        + IMAGE_ENTRY + " in " + pack);
                return null;
            }
            try (InputStream input = archive.getInputStream(entry)) {
                startupImage = ImageIO.read(input);
            }
            if (startupImage == null) {
                LOG.severe("Java could not decode startup image: " + pack);
                return null;
            }
            LOG.info("Loaded Sklotopolis startup image directly: "
                    + startupImage.getWidth() + "x"
                    + startupImage.getHeight() + " from " + pack);
            return startupImage;
        } catch (IOException error) {
            LOG.log(Level.SEVERE,
                    "Unable to read startup image directly from " + pack,
                    error);
            return null;
        }
    }

    private static File resolvePack() {
        File pack = new File(HighResStartupSettings.resourcePack);
        if (!pack.isAbsolute()) {
            pack = new File(System.getProperty("user.dir"),
                    HighResStartupSettings.resourcePack);
        }
        return pack.getAbsoluteFile();
    }
}
