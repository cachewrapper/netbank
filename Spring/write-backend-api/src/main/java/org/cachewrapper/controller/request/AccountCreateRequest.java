package org.cachewrapper.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request body for creating a new user account.
 * <p>
 * This record represents the data required to create an account via the API.
 * It is used as the input for {@link org.cachewrapper.controller.UserV1Controller#createAccount}.
 * </p>
 *
 * Fields:
 * <ul>
 *     <li>{@code userUUID} - the unique identifier of the user/account</li>
 *     <li>{@code username} - the username associated with the account</li>
 *     <li>{@code balance} - the initial balance of the account; must be zero or positive</li>
 * </ul>
 *
 * All fields are mandatory and are validated by the controller before processing.
 */
public record AccountCreateRequest(
        UUID userUUID,
        String username,
        BigDecimal balance
) {}