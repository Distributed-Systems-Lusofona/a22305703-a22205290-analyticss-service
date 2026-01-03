package pt.ulusofona.cd.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank
    @Size(min = 10, max = 200)
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Size(max = 50)
    @Column(name = "contact_person", length = 50)
    private String contactPerson;

    @Size(max = 20)
    @Column(name = "tax_number", unique = true, length = 20)
    private String taxNumber;

    @Size(max = 50)
    @Column(name = "country", length = 50)
    private String country;

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in YYYY-MM-DD format")
    @Column(name = "established_date", length = 10)
    private String establishedDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}