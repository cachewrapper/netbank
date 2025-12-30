package org.cachewrapper.paper.command.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.command.PaperCommand;
import org.cachewrapper.paper.rest.impl.AccountRestClientImpl;
import org.incendo.cloud.Command;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class CreateAccountCommand implements PaperCommand {

    private final AccountRestClientImpl accountRestClient;

    @Override
    public @NotNull Command<? extends Source> command(@NotNull PaperCommandManager<Source> commandManager) {
        return commandManager.commandBuilder("account")
                .literal("create")
                .senderType(PlayerSource.class)
                .handler(source -> {
                    var player = source.sender().source();
                    var playerUUID = player.getUniqueId();

                    accountRestClient
                            .createAccountAsync(playerUUID, player.getName(), BigDecimal.valueOf(100.000))
                            .thenRun(() -> player.sendMessage("You've created an account!"));
                })
                .build();
    }
}