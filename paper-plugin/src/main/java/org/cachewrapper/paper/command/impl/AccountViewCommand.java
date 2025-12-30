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
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class AccountViewCommand implements PaperCommand {

    private static final List<String> ACCOUNT_VIEW = List.of(
            "",
            " Account UUID: %uuid%",
            " Balance: %balance%",
            ""
    );

    private final AccountRestClientImpl accountRestClient;

    @Override
    public @NotNull Command<? extends Source> command(@NotNull PaperCommandManager<Source> commandManager) {
        return commandManager.commandBuilder("account")
                .senderType(PlayerSource.class)
                .handler(source -> {
                    var player = source.sender().source();
                    var playerUUID = player.getUniqueId();

                    accountRestClient.loadAccountAsync(playerUUID)
                            .thenAcceptAsync(account ->
                                    ACCOUNT_VIEW.forEach(line ->
                                            player.sendMessage(line
                                                    .replaceAll("%uuid%", account.getAccountUUID().toString())
                                                    .replaceAll("%balance%", account.getBalance().toString())
                                            )));
                })
                .build();
    }
}