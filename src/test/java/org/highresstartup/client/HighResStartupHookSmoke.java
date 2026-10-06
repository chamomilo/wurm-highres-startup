package org.highresstartup.client;

import javassist.ClassPool;
import javassist.CtClass;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class HighResStartupHookSmoke {
    private HighResStartupHookSmoke() {
    }

    public static void main(String[] arguments) throws Exception {
        if (arguments.length != 1) {
            throw new IllegalArgumentException("Expected resource-pack path");
        }

        Properties properties = new Properties();
        properties.setProperty("resourcePack", arguments[0]);
        HighResStartupMod mod = new HighResStartupMod();
        mod.configure(properties);
        mod.preInit();

        ClassPool pool = HookManager.getInstance().getClassPool();
        CtClass renderer = pool.getCtClass(
                "com.wurmonline.client.startup.splash.StartupRenderer");
        String transformed = new String(renderer.toBytecode(),
                StandardCharsets.ISO_8859_1);
        if (!transformed.contains("HighResStartupResources")
                || !transformed.contains("loadStartupImage")) {
            throw new AssertionError(
                    "StartupRenderer does not call the direct image loader");
        }

        BufferedImage image = HighResStartupResources.loadStartupImage();
        if (image == null || image.getWidth() != 3840
                || image.getHeight() != 2160) {
            throw new AssertionError("Direct startup image is not 3840x2160");
        }
        System.out.println("Verified direct StartupRenderer hook and "
                + image.getWidth() + "x" + image.getHeight() + " image");
    }
}
