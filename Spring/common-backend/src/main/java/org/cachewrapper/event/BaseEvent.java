package org.cachewrapper.event;

import jakarta.persistence.*;
import lombok.Data;
import org.cachewrapper.event.converter.DomainObjectConverter;
import org.cachewrapper.payload.Payload;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type")
@Table(name = "events")
public abstract class BaseEvent<T extends Payload> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "aggregate_uuid")
    private UUID aggregateUUID;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = DomainObjectConverter.class)
    @Column(name = "payload")
    private T payload;

    public BaseEvent(@NotNull UUID aggregateUUID, @NotNull T payload) {
        this.aggregateUUID = aggregateUUID;
        this.payload = payload;
    }

    public BaseEvent() {

    }
}