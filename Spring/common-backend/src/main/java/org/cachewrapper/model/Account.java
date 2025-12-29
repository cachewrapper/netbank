package org.cachewrapper.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@Scope("prototype")
public class Account {

    @Id
    @Column(name = "account_uuid")
    private UUID accountUUID;

    private BigDecimal balance;

    public Account(@NotNull UUID accountUUID, @NotNull BigDecimal balance) {
        this.accountUUID = accountUUID;
        this.balance = balance;
    }
}