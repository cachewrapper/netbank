package org.cachewrapper.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request body for sending money between two user accounts.
 * <p>
 * This record represents the data required to perform a money transfer via the API.
 * It is used as the input for {@link org.cachewrapper.controller.UserV1Controller#sendMoney}.
 * </p>
 *
 * Fields:
 * <ul>
 *     <li>{@code senderAccountUUID} - the unique identifier of the account sending the money</li>
 *     <li>{@code receiverAccountUUID} - the unique identifier of the account receiving the money</li>
 *     <li>{@code transactionAmount} - the amount to be transferred; must be greater than zero</li>
 * </ul>
 *
 * All fields are mandatory and are validated by the controller before processing.
 */
public record MoneySendRequest(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) {}
