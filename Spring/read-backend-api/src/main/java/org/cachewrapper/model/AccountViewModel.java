package org.cachewrapper.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountViewModel {

    @Id
    @Column(name = "account_uuid")
    private UUID accountUUID;

    @Column(name = "username")
    private String username;

    @Column(name = "balance")
    private BigDecimal balance;
}