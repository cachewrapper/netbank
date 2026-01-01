package org.cachewrapper.paper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.cachewrapper.paper.guice.PluginModule;
import org.cachewrapper.paper.installer.Installer;
import org.cachewrapper.paper.installer.impl.BukkitCommandInstaller;
import org.cachewrapper.paper.instantiator.Instantiator;
import org.cachewrapper.paper.instantiator.impl.PaperCommandManagerInstantiator;

import java.util.List;

@Getter
public class NetbankPaper extends JavaPlugin {

    private static final List<Class<? extends Instantiator<?>>> INSTANTIATORS = List.of(
            PaperCommandManagerInstantiator.class
    );

    private static final List<Class<? extends Installer>> INSTALLERS = List.of(
            BukkitCommandInstaller.class
    );

    private final Injector injector;

    public NetbankPaper() {
        var pluginModule = new PluginModule(this);
        this.injector = Guice.createInjector(pluginModule);
    }

    @Override
    public void onEnable() {
        initInstantiators();
        initInstallers();
    }

    private void initInstantiators() {
        INSTANTIATORS.stream()
                .map(injector::getInstance)
                .forEach(Instantiator::init);
    }

    private void initInstallers() {
        INSTALLERS.stream()
                .map(injector::getInstance)
                .forEach(Installer::install);
    }
}