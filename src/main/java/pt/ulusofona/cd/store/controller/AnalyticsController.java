package pt.ulusofona.cd.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.store.dto.RestaurantMetricsResponse;
import pt.ulusofona.cd.store.dto.TopRestaurantDto;
import pt.ulusofona.cd.store.service.AnalyticsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
//ssss
    private final AnalyticsService analyticsService;

    /**
     * Ex.: GET /api/v1/analytics/restaurants/10/metrics
     */
    @GetMapping("/restaurants/{restaurantId}/metrics")
    public ResponseEntity<RestaurantMetricsResponse> getRestaurantMetrics(
            @PathVariable Long restaurantId
    ) {
        return ResponseEntity.ok(analyticsService.getMetricsByRestaurantId(restaurantId));
    }

    /**
     * Ex.: GET /api/v1/analytics/top-restaurants?limit=10
     * Ordena por confirmedCount desc.
     */
    @GetMapping("/top-restaurants")
    public ResponseEntity<List<TopRestaurantDto>> getTopRestaurants(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(analyticsService.getTopRestaurantsByConfirmed(limit));
    }
}
