package org.cachewrapper.repository;

import org.cachewrapper.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Modifying
    @Query("""
                UPDATE Account account
                SET account.balance = account.balance + :amount
                WHERE account.accountUUID = :userUUID
            """)
    void add(@Param("userUUID") UUID userUUID, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("""
                UPDATE Account account
                SET account.balance = account.balance - :amount
                WHERE account.accountUUID = :userUUID
            """)
    void subtract(@Param("userUUID") UUID userUUID, @Param("amount") BigDecimal amount);
}