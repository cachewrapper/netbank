package org.cachewrapper.paper.command.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.command.PaperCommand;
import org.incendo.cloud.Command;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.standard.IntegerParser;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class TestCommand implements PaperCommand {

    @Override
    public @NotNull Command<? extends Source> command(@NotNull PaperCommandManager<Source> commandManager) {
        return commandManager.commandBuilder("test")
                .required("test-arg", IntegerParser.integerParser())
                .senderType(PlayerSource.class)
                .handler(source -> {
                    var player = source.sender().source();
                    var testArg = source.get("test-arg");

                    player.sendMessage("Your argument is: " + testArg);
                })
                .build();
    }
}