import javassist.ClassPool;
import javassist.Loader;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/** Runs the packaged mod with Wurm's loader and no mod resources on the parent classpath. */
public final class HighResStartupSharedLoaderSmoke {
    public static void main(String[] arguments) throws Exception {
        if (arguments.length != 2) throw new IllegalArgumentException("Expected mod JAR and resource pack");
        Path homes = Paths.get(System.getProperty("user.home"));
        Files.createDirectories(homes);
        Path home = Files.createTempDirectory(homes, "settings-");
        System.setProperty("user.home", home.toString());
        HookManager manager = HookManager.getInstance();
        ClassPool pool = manager.getClassPool();
        Loader loader = manager.getLoader();
        loader.delegateLoadingOf("org.gotti.wurmunlimited.modloader.classhooks.");
        loader.delegateLoadingOf("javassist.");
        pool.appendClassPath(new File(arguments[0]).getAbsolutePath());

        Class<?> product = loader.loadClass("org.highresstartup.client.HighResStartupMod");
        check(product.getClassLoader() == loader, "Mod must use Wurm's shared loader");
        check(product.getResourceAsStream("screens.properties") == null,
                "Parent classpath must not hide the packaged catalogue regression");
        Object mod = product.newInstance();
        Properties properties = new Properties();
        properties.setProperty("resourcePack", new File(arguments[1]).getAbsolutePath());
        properties.setProperty("startupScreen", "wurm-reimagined");
        product.getMethod("configure", Properties.class).invoke(mod, properties);

        Class<?> library = loader.loadClass("org.highresstartup.client.HighResStartupLibrary");
        Method choices = library.getDeclaredMethod("choices");
        choices.setAccessible(true);
        String[] actual = (String[]) choices.invoke(null);
        check(java.util.Arrays.equals(actual, new String[]{"random", "sklotopolis", "wurm-reimagined"}),
                "Settings must expose the packaged catalogue");
        product.getMethod("preInit").invoke(mod);
        product.getMethod("init").invoke(mod);
        String renderer = new String(pool.get("com.wurmonline.client.startup.splash.StartupRenderer")
                .toBytecode(), StandardCharsets.ISO_8859_1);
        check(renderer.contains("HighResStartupResources") && renderer.contains("loadStartupImage"),
                "Native startup renderer must call the mod's image loader");

        Class<?> resources = loader.loadClass("org.highresstartup.client.HighResStartupResources");
        Method load = resources.getMethod("loadStartupImage");
        BufferedImage image = (BufferedImage) load.invoke(null);
        check(image != null && image.getWidth() == 3840 && image.getHeight() == 2160,
                "Packaged screen must load at 3840x2160");
        check(image == load.invoke(null), "Reconnect must retain the selected image");

        Class<?> settings = loader.loadClass("org.chamomilo.wurm.update.SharedModSettings");
        Object provider = settings.getMethod("provider", String.class).invoke(null, "highres-startup");
        check(provider != null, "Shared updater must expose the mod's Settings provider");
        Class<?> providerType = loader.loadClass("org.chamomilo.wurm.update.SharedModSettings$Provider");
        List<?> specs = (List<?>) providerType.getMethod("settings").invoke(provider);
        check(specs.size() == 2, "Settings must include screen choice and resource pack");
        Method value = providerType.getMethod("value", String.class);
        check("wurm-reimagined".equals(value.invoke(provider, "startupScreen")),
                "Settings must reflect the configured screen");
        providerType.getMethod("change", String.class, String.class)
                .invoke(provider, "startupScreen", "sklotopolis");
        check("sklotopolis".equals(value.invoke(provider, "startupScreen")),
                "Settings must accept the next-start screen choice");
        Properties saved = new Properties();
        try (InputStream input = Files.newInputStream(home.resolve(".chamomilo/mod-settings/highres-startup.properties"))) {
            saved.load(input);
        }
        check(saved.size() == 1 && "sklotopolis".equals(saved.getProperty("startupScreen")),
                "Save only the explicit screen override, without pinning the resource-pack version");
        check(image == load.invoke(null), "Startup-only settings must preserve the current image until restart");
        System.out.println("STARTUP_SHARED_LOADER_OK: packaged catalogue, configure, lifecycle, native hook, 4K image, reconnect and saved updater settings");
    }

    private static void check(boolean value, String description) {
        if (!value) throw new AssertionError(description);
    }
}
