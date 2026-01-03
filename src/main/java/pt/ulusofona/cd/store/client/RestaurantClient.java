package pt.ulusofona.cd.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ulusofona.cd.store.dto.RestaurantDto;

@FeignClient(
        name = "restaurant-service",
        url = "${restaurant.service.url}"
)
public interface RestaurantClient {

    @GetMapping("/api/v1/restaurants/{id}")
    RestaurantDto getRestaurantById(@PathVariable("id") Long id);
}
