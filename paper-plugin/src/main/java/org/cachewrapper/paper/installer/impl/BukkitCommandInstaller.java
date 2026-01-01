package org.cachewrapper.paper.installer.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.command.PaperCommand;
import org.cachewrapper.paper.installer.Installer;
import org.cachewrapper.paper.instantiator.impl.PaperCommandManagerInstantiator;
import org.reflections.Reflections;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class BukkitCommandInstaller implements Installer {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper.paper";

    private final Injector injector;
    private final PaperCommandManagerInstantiator paperCommandManagerInstantiator;

    @Override
    public void install() {
        var reflections = new Reflections(LOOKUP_PACKAGE);
        var commandClassesSet = reflections.getSubTypesOf(PaperCommand.class);

        var commandManager = paperCommandManagerInstantiator.get();
        commandClassesSet.stream()
                .map(injector::getInstance)
                .forEach(paperCommand -> {
                    var command = paperCommand.command(commandManager);
                    commandManager.command(command);
                });
    }
}