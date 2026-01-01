package org.cachewrapper.repository;

import org.cachewrapper.model.AccountViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Repository for managing {@link AccountViewModel} entities.
 * <p>
 * Provides basic CRUD operations through {@link JpaRepository} and
 * custom methods for updating account balances.
 * </p>
 */
@Repository
public interface AccountViewRepository extends JpaRepository<AccountViewModel, UUID> {

    /**
     * Increases the balance of the specified account by the given amount.
     *
     * @param accountUUID the UUID of the account to update
     * @param amount      the amount to add to the balance
     */
    @Modifying
    @Query("""
                UPDATE AccountViewModel account
                SET account.balance = account.balance + :amount
                WHERE account.accountUUID = :accountUUID
            """)
    void addBalance(@Param("accountUUID") UUID accountUUID, @Param("amount") BigDecimal amount);

    /**
     * Decreases the balance of the specified account by the given amount.
     *
     * @param accountUUID the UUID of the account to update
     * @param amount      the amount to subtract from the balance
     */
    @Modifying
    @Query("""
                UPDATE AccountViewModel account
                SET account.balance = account.balance - :amount
                WHERE account.accountUUID = :accountUUID
            """)
    void subtractBalance(@Param("accountUUID") UUID accountUUID, @Param("amount") BigDecimal amount);
}