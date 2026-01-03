package pt.ulusofona.cd.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
    private Long id;
    private String name;
    private String city;
    private String country;
}
