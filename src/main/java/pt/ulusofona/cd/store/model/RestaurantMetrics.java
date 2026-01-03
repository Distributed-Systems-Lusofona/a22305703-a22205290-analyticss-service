package pt.ulusofona.cd.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "restaurant_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMetrics {

    @Id
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "created_count", nullable = false)
    private long createdCount = 0;

    @Column(name = "confirmed_count", nullable = false)
    private long confirmedCount = 0;

    @Column(name = "cancelled_count", nullable = false)
    private long cancelledCount = 0;

    @Column(name = "notified_count", nullable = false)
    private long notifiedCount = 0;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
