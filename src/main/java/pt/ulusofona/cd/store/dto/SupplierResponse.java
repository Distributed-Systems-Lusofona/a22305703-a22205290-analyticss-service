package pt.ulusofona.cd.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class SupplierResponse {
    private UUID id;
    private String companyName;
    private String email;
    private String address;
    private String phoneNumber;
    private String contactPerson;
    private String taxNumber;
    private String country;
    private String city;
    private String establishedDate;
    private boolean isActive;
}