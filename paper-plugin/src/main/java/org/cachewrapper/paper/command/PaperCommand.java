package org.cachewrapper.paper.command;

import org.incendo.cloud.Command;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.Source;
import org.jetbrains.annotations.NotNull;

public interface PaperCommand {

    @NotNull
    Command<? extends Source> command(@NotNull PaperCommandManager<Source> commandManager);
}