package pt.ulusofona.cd.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "processed_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEvent {

    @Id
    @Column(name = "event_key", nullable = false, length = 200)
    private String eventKey;

    @Column(name = "event_type", nullable = false, length = 60)
    private String eventType;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "processed_at", nullable = false, updatable = false)
    private Instant processedAt;

    @PrePersist
    protected void onCreate() {
        if (eventKey != null) eventKey = eventKey.trim();
        processedAt = Instant.now();
    }
}
