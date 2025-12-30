package org.cachewrapper.paper.rest.impl;

import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.rest.domain.Account;
import org.cachewrapper.paper.rest.type.AccountRestClient;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor
public class AccountRestClientImpl implements AccountRestClient {

    @Override
    public @NotNull String getRepository() {
        return "/api/v1/users";
    }

    @Override
    public @NotNull Class<Account> getEntityClass() {
        return Account.class;
    }
}