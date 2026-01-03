package pt.ulusofona.cd.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMetricsResponse {

    private Long restaurantId;

    private long createdCount;
    private long confirmedCount;
    private long cancelledCount;
    private long notifiedCount;

    private Instant updatedAt;
}
