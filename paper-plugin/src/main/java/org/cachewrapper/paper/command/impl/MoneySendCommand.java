package org.cachewrapper.paper.command.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.cachewrapper.paper.command.PaperCommand;
import org.cachewrapper.paper.rest.impl.AccountRestClientImpl;
import org.incendo.cloud.Command;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.standard.LongParser;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class MoneySendCommand implements PaperCommand {

    private final AccountRestClientImpl accountRestClient;

    @Override
    public @NotNull Command<? extends Source> command(@NotNull PaperCommandManager<Source> commandManager) {
        return commandManager.commandBuilder("money")
                .literal("send")
                .required("receiverUUID", PlayerParser.playerParser())
                .required("transactionAmount", LongParser.longParser())
                .senderType(PlayerSource.class)
                .handler(source -> {
                    var player = source.sender().source();
                    var playerUUID = player.getUniqueId();

                    Player receiver = source.get("receiverUUID");
                    var receiverUUID = receiver.getUniqueId();

                    long transactionAmount = source.get("transactionAmount");
                    accountRestClient.sendMoneyAsync(playerUUID, receiverUUID, BigDecimal.valueOf(transactionAmount))
                            .thenRun(() -> player.sendMessage("You've sent %s to player %s!".formatted(receiver.getName(), transactionAmount)));
                })
                .build();
    }
}
