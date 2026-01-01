package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.model.AccountViewModel;
import org.cachewrapper.repository.AccountViewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AccountViewController {

    private final AccountViewRepository accountViewRepository;

    @GetMapping("/{accountUUID}")
    public AccountViewModel loadAccount(@PathVariable UUID accountUUID) {
        return accountViewRepository.findById(accountUUID).orElse(null);
    }
}