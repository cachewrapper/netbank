package org.cachewrapper.paper.rest.type;

import org.cachewrapper.paper.rest.async.RestClientAsync;
import org.cachewrapper.paper.rest.domain.Account;
import org.cachewrapper.paper.rest.request.impl.AccountCreatePayload;
import org.cachewrapper.paper.rest.request.impl.MoneySendPayload;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface AccountRestClient extends RestClientAsync<UUID, Account> {

    default CompletableFuture<Account> loadAccountAsync(@NotNull UUID userUUID) {
        return loadAsync(userUUID);
    }

    @NotNull
    default CompletableFuture<Account> createAccountAsync(
            @NotNull UUID userUUID,
            @NotNull String username,
            @NotNull BigDecimal balance
    ) {
        var accountCreatePayload = new AccountCreatePayload(userUUID, username, balance);
        return this.postAsync(accountCreatePayload, "/create");
    }

    default CompletableFuture<Account> sendMoneyAsync(
            @NotNull UUID senderUserUUID,
            @NotNull UUID receiverUserUUID,
            @NotNull BigDecimal amount
    ) {
        var moneySendPayload = new MoneySendPayload(senderUserUUID, receiverUserUUID, amount);
        return this.postAsync(moneySendPayload, "/money/send");
    }
}