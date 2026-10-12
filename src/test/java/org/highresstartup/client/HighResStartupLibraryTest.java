package org.highresstartup.client;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

public class HighResStartupLibraryTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test public void fixedChoiceOverridesRandomOrder() {
        for (HighResStartupLibrary.Screen screen : HighResStartupLibrary.SCREENS) {
            for (int draw = 0; draw < 2; draw++) {
                List<HighResStartupLibrary.Screen> candidates =
                        HighResStartupResources.candidates(screen.id, draw(draw));
                assertEquals(screen.id, candidates.get(0).id);
                assertEquals(2, new HashSet<HighResStartupLibrary.Screen>(candidates).size());
            }
        }
    }

    @Test public void eachScreenCanBeSelectedRandomly() {
        Set<String> selected = new HashSet<String>();
        for (int draw = 0; draw < 2; draw++) {
            selected.add(HighResStartupResources.candidates("random", draw(draw)).get(0).id);
        }
        assertEquals(new HashSet<String>(java.util.Arrays.asList("sklotopolis", "wurm-reimagined")), selected);
    }

    @Test public void missingCorruptAndWrongSizeImagesFallBackToOtherScreen() throws Exception {
        for (byte[] badImage : new byte[][]{null, new byte[]{1, 2, 3}, png(64, 36)}) {
            File pack = pack(badImage, png(3840, 2160));
            BufferedImage image = HighResStartupResources.loadFromPack(pack, "sklotopolis", draw(0));
            assertNotNull(image);
            assertEquals(3840, image.getWidth());
            assertEquals(2160, image.getHeight());
            image.flush();
        }
    }

    @Test public void noUsableArtworkAllowsStockFallback() throws Exception {
        assertNull(HighResStartupResources.loadFromPack(pack(new byte[]{1}, null), "random", draw(0)));
        assertNull(HighResStartupResources.loadFromPack(new File(temporary.getRoot(), "absent.jar"), "random", draw(0)));
        File broken = temporary.newFile();
        assertNull(HighResStartupResources.loadFromPack(broken, "random", draw(0)));
    }

    @Test public void selectionDefaultsAndInvalidValuesChooseRandom() {
        Properties properties = new Properties();
        HighResStartupSettings.configure(properties);
        assertEquals("random", HighResStartupSettings.startupScreen);
        properties.setProperty("startupScreen", " WURM-REIMAGINED ");
        HighResStartupSettings.configure(properties);
        assertEquals("wurm-reimagined", HighResStartupSettings.startupScreen);
        properties.setProperty("startupScreen", "missing-screen");
        HighResStartupSettings.configure(properties);
        assertEquals("random", HighResStartupSettings.startupScreen);
    }

    @Test public void settingsExposeEntireLibraryAndRequireRestart() {
        org.chamomilo.wurm.update.ModSetting setting = ModSettingsSchema.specs().get(0);
        assertEquals("startupScreen", setting.key);
        assertFalse(setting.live);
        assertArrayEquals(HighResStartupLibrary.choices(), setting.choices());
    }

    private static Random draw(final int choice) {
        return new Random(0) {
            @Override public int nextInt(int bound) { return choice % bound; }
        };
    }

    private File pack(byte[] first, byte[] second) throws IOException {
        File file = temporary.newFile();
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(file))) {
            byte[][] images = {first, second};
            for (int i = 0; i < images.length; i++) {
                if (images[i] == null) continue;
                zip.putNextEntry(new ZipEntry(HighResStartupLibrary.SCREENS.get(i).imageEntry));
                zip.write(images[i]);
                zip.closeEntry();
            }
        }
        return file;
    }

    private static byte[] png(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", bytes);
            return bytes.toByteArray();
        } finally { image.flush(); }
    }
}
