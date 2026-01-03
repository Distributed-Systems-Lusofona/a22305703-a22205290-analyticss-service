package pt.ulusofona.cd.store.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SupplierRequest {
    @NotBlank
    @Size(min = 2, max = 100)
    private String companyName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 10, max = 200)
    private String address;

    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Phone number must be valid"
    )
    private String phoneNumber;

    @Size(max = 50)
    private String contactPerson;

    @Size(max = 20)
    private String taxNumber;

    @Size(max = 50)
    private String country;

    @Size(max = 50)
    private String city;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Date must be in YYYY-MM-DD format"
    )
    private String establishedDate;

    private boolean isActive = true;
}