package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.coordinator.AccountCommandCoordinator;
import org.cachewrapper.command.coordinator.MoneyCommandCoordinator;
import org.cachewrapper.controller.request.AccountCreateRequest;
import org.cachewrapper.controller.request.MoneySendRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for user-related operations (version 1).
 * <p>
 * Exposes endpoints to manage user accounts and perform money transfers.
 * All requests are routed to the corresponding command services, which
 * handle business logic, create commands, and dispatch them to the relevant handlers.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserV1Controller {

    private final AccountCommandCoordinator accountCommandService;
    private final MoneyCommandCoordinator moneyCommandService;

    /**
     * Creates a new user account.
     * <p>
     * Receives an {@link AccountCreateRequest} containing the UUID, username, and initial balance.
     * Delegates account creation to {@link AccountCommandCoordinator} and returns the appropriate
     * HTTP response:
     * <ul>
     *     <li>201 CREATED if account is successfully created</li>
     *     <li>409 CONFLICT if an account with the given UUID already exists</li>
     * </ul>
     *
     * @param accountCreateRequest the request body containing account details
     * @return ResponseEntity with HTTP status reflecting the result
     */
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
        var userUUID = accountCreateRequest.userUUID();
        var username = accountCreateRequest.username();
        var balance = accountCreateRequest.balance();

        return accountCommandService.createAccount(userUUID, username, balance);
    }

    /**
     * Sends money from one account to another.
     * <p>
     * Receives a {@link MoneySendRequest} containing sender and receiver UUIDs and the transaction amount.
     * Delegates the transfer logic to {@link MoneyCommandCoordinator} and returns the appropriate HTTP response:
     * <ul>
     *     <li>200 OK if transaction succeeds</li>
     *     <li>404 NOT FOUND if either account does not exist</li>
     *     <li>400 BAD REQUEST if the sender does not have enough balance</li>
     * </ul>
     *
     * @param moneySendRequest the request body containing transaction details
     * @return ResponseEntity with HTTP status reflecting the result
     */
    @PostMapping("/money/send")
    public ResponseEntity<String> sendMoney(@RequestBody MoneySendRequest moneySendRequest) {
        var senderAccountUUID = moneySendRequest.senderAccountUUID();
        var receiverAccountUUID = moneySendRequest.receiverAccountUUID();
        var transactionAmount = moneySendRequest.transactionAmount();

        return moneyCommandService.createSendMoney(senderAccountUUID, receiverAccountUUID, transactionAmount);
    }
}