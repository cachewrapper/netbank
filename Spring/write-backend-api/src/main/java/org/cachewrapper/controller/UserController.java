package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.controller.request.AccountCreateRequest;
import org.cachewrapper.controller.request.MoneySendRequest;
import org.cachewrapper.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> accountCreate(@RequestBody AccountCreateRequest accountCreateRequest) {
        var userUUID = accountCreateRequest.userUUID();
        var balance = accountCreateRequest.balance();

        return accountService.creatAccount(userUUID, balance);
    }

    @PostMapping("/money/send")
    public ResponseEntity<String> moneySend(@RequestBody MoneySendRequest moneySendRequest) {
        var senderAccountUUID = moneySendRequest.senderAccountUUID();
        var receiverAccountUUID = moneySendRequest.receiverAccountUUID();
        var transactionAmount = moneySendRequest.transactionAmount();

        return accountService.sendMoney(senderAccountUUID, receiverAccountUUID, transactionAmount);
    }
}