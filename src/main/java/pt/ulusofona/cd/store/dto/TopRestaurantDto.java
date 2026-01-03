package pt.ulusofona.cd.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopRestaurantDto {

    private Long restaurantId;

    // Ex.: "created", "confirmed", "cancelled", "notified"
    private String metric;

    private long value;
}
