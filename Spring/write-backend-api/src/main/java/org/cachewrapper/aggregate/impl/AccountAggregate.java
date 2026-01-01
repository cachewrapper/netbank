package org.cachewrapper.aggregate.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.cachewrapper.aggregate.Aggregate;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.event.impl.MoneyReceiveEvent;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.exception.BalanceLessThanZeroException;
import org.cachewrapper.exception.TransactionAmountThanZeroException;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Aggregate representing a bank account.
 *
 * <p>This aggregate handles the application of domain events that modify its state:
 * <ul>
 *     <li>{@link AccountCreatedEvent} — initializes the account.</li>
 *     <li>{@link MoneySendEvent} — decreases the balance when money is sent.</li>
 *     <li>{@link MoneyReceiveEvent} — increases the balance when money is received.</li>
 * </ul>
 *
 * <p>Validation is performed on the balance and transaction amounts to ensure
 * that no invalid state (e.g., negative balances) occurs.
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "account_aggregates")
public class AccountAggregate extends Aggregate {

    @Column(name = "username")
    private String username;

    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * Applies the account creation event to initialize the aggregate state.
     *
     * @param accountCreatedEvent the event containing the account creation data
     * @throws BalanceLessThanZeroException if the initial balance is negative
     */
    @NotNull
    public void applyAccountCreatedEvent(@NotNull AccountCreatedEvent accountCreatedEvent) {
        var accountCreatedPayload = accountCreatedEvent.getPayload();

        var accountUUID = accountCreatedPayload.accountUUID();
        var username = accountCreatedPayload.username();
        var balance = accountCreatedPayload.balance();

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BalanceLessThanZeroException();
        }

        this.aggregateUUID = accountUUID;
        this.username = username;
        this.balance = balance;
    }

    /**
     * Applies a money send event, decreasing the balance by the transaction amount.
     *
     * @param moneySendEvent the event containing the transaction data
     * @throws TransactionAmountThanZeroException if the transaction amount is negative
     */
    public void applyMoneySendEvent(@NotNull MoneySendEvent moneySendEvent) {
        var moneySendPayload = moneySendEvent.getPayload();
        var transactionAmount = moneySendPayload.transactionAmount();

        if (transactionAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionAmountThanZeroException();
        }

        balance = balance.subtract(transactionAmount);
    }

    /**
     * Applies a money receive event, increasing the balance by the transaction amount.
     *
     * @param moneyReceiveEvent the event containing the transaction data
     * @throws TransactionAmountThanZeroException if the transaction amount is negative
     */
    public void applyMoneyReceiveEvent(@NotNull MoneyReceiveEvent moneyReceiveEvent) {
        var moneyReceivePayload = moneyReceiveEvent.getPayload();
        var transactionAmount = moneyReceivePayload.transactionAmount();

        if (transactionAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionAmountThanZeroException();
        }

        balance = balance.add(transactionAmount);
    }
}