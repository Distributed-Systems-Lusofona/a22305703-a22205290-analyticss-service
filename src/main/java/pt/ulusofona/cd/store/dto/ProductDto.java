package pt.ulusofona.cd.store.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 120, message = "Name must be between 3 and 120 characters")
    private String name;

    private String description;

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
    private BigDecimal price;

    @Min(value = 0, message = "Stock must be non-negative")
    private int stock;

    private String currency = "EUR";
    private UUID supplierId;
    private Instant createdAt;
    private Instant updatedAt;
}