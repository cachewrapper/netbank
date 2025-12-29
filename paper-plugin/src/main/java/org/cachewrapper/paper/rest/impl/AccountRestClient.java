package org.cachewrapper.paper.rest.impl;

import org.cachewrapper.paper.rest.async.RestClientAsync;
import org.cachewrapper.paper.rest.domain.Account;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AccountRestClient implements RestClientAsync<UUID, Account> {

    @Override
    public @NotNull String getRepository() {
        return "";
    }

    @Override
    public @NotNull Class<?> getEntityClass() {
        return null;
    }
}
