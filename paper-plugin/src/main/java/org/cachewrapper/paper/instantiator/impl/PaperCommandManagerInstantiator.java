package org.cachewrapper.paper.instantiator.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.NetbankPaper;
import org.cachewrapper.paper.instantiator.Instantiator;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper;
import org.incendo.cloud.paper.util.sender.Source;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PaperCommandManagerInstantiator implements Instantiator<PaperCommandManager<Source>> {

    private final NetbankPaper plugin;

    @Override
    public @NotNull PaperCommandManager<Source> initialize() {
        return PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(plugin);
    }
}