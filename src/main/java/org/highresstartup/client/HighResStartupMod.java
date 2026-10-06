package org.highresstartup.client;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmClientMod;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Loads the 4K branded startup-screen resource pack. */
public final class HighResStartupMod implements org.gotti.wurmunlimited.modloader.interfaces.ModListener, WurmClientMod, Configurable,
        PreInitable, Initable {
    public static final String VERSION = "0.3.6";
    private static final Logger LOG = Logger.getLogger("HighResStartup");

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public void configure(Properties properties) {
        HighResStartupSettings.configure(properties);
    }

    @Override
    public void preInit() {
        org.chamomilo.wurm.update.SharedUpdateHooks.install();
        try {
            ClassPool pool = HookManager.getInstance().getClassPool();
            CtClass renderer = pool.getCtClass(
                    "com.wurmonline.client.startup.splash.StartupRenderer");
            for (CtConstructor constructor : renderer.getDeclaredConstructors()) {
                constructor.instrument(new ExprEditor() {
                    @Override
                    public void edit(MethodCall call) {
                        if (!"com.wurmonline.client.resources.Resources"
                                .equals(call.getClassName())
                                || !"getResourceAsImage"
                                .equals(call.getMethodName())) {
                            return;
                        }
                        try {
                            call.replace("{ $_ = org.highresstartup.client."
                                    + "HighResStartupResources.loadStartupImage();"
                                    + " if ($_ == null) $_ = $proceed($$); }");
                        } catch (Exception error) {
                            throw new RuntimeException(error);
                        }
                    }
                });
            }
            LOG.info("Direct startup-image hook installed");
        } catch (Throwable error) {
            LOG.log(Level.SEVERE,
                    "Unable to install startup-screen resource hook", error);
            throw new RuntimeException(
                    "Unable to install startup-screen resource hook", error);
        }
    }

    @Override
    public void init() {
        org.chamomilo.wurm.update.SharedUpdateHooks.registerHost("highres-startup");
        LOG.info("High-resolution Sklotopolis startup screen " + VERSION
                + " initialized");
    }

    @Override public void modInitialized(org.gotti.wurmunlimited.modloader.interfaces.ModEntry<?> entry) {
        org.chamomilo.wurm.update.SharedUpdateCoordinator.modInitialized(entry);
    }}
