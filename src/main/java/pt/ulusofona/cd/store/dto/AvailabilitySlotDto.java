package pt.ulusofona.cd.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AvailabilitySlotDto {
    private Long id;
    private Long restaurantId;       // se no Restaurant service for String/UUID muda aqui
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;
    private Integer seatsAvailable;
}
