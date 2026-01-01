package org.cachewrapper.aggregate;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

/**
 * Base abstract class for all domain aggregates.
 * <p>
 * Each aggregate represents a consistent business entity and its state in the system.
 * This class uses {@link InheritanceType#TABLE_PER_CLASS}, meaning each concrete subclass
 * will be mapped to its own database table.
 * <p>
 * The primary key for each aggregate is a UUID, ensuring global uniqueness across all aggregates.
 * Subclasses should extend this class and implement domain-specific logic for handling events
 * and maintaining state.
 * <p>
 * Example usage:
 * <pre>
 *     {@code
 *     public class AccountAggregate extends Aggregate {
 *         private String username;
 *         private BigDecimal balance;
 *
 *         public void applyAccountCreatedEvent(AccountCreatedEvent event) { ... }
 *         public void applyMoneySendEvent(MoneySendEvent event) { ... }
 *         public void applyMoneyReceiveEvent(MoneyReceiveEvent event) { ... }
 *     }
 *     }
 * </pre>
 * <p>
 * Important notes:
 * <ul>
 *     <li>UUID is used as the primary key and stored in a database-native UUID column.</li>
 *     <li>TABLE_PER_CLASS inheritance avoids a single table for all aggregates, giving
 *         each aggregate its own table structure.</li>
 *     <li>Queries across multiple aggregate types will require unions internally.</li>
 * </ul>
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Aggregate {

    /**
     * Unique identifier for this aggregate.
     * Stored as a native UUID in the database.
     */
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "aggregate_uuid")
    protected UUID aggregateUUID;
}