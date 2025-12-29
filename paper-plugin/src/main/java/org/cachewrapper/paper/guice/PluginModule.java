package org.cachewrapper.paper.guice;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.NetbankPaper;

@RequiredArgsConstructor
public class PluginModule extends AbstractModule {

    private final NetbankPaper plugin;

    @Override
    protected void configure() {
        bind(NetbankPaper.class).toInstance(plugin);
    }
}