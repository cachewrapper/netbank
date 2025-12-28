package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.model.Account;
import org.cachewrapper.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping("/{uuid}")
    public Account loadAccount(@PathVariable UUID uuid) {
        return accountRepository.findById(uuid)
                .orElse(null);
    }
}