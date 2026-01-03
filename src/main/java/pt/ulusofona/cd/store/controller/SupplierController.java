package pt.ulusofona.cd.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulusofona.cd.store.dto.ProductDto;
import pt.ulusofona.cd.store.dto.SupplierRequest;
import pt.ulusofona.cd.store.dto.SupplierResponse;
import pt.ulusofona.cd.store.mapper.SupplierMapper;
import pt.ulusofona.cd.store.model.Supplier;

import jakarta.validation.Valid;
import pt.ulusofona.cd.store.service.SupplierService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody SupplierRequest supplier) {
        Supplier created = supplierService.createSupplier(supplier);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SupplierMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable UUID id) {
        Supplier supplier = supplierService.getSupplierById(id);
        System.out.println(supplier.isActive());
        System.out.println(SupplierMapper.toResponse(supplier).isActive());
        return ResponseEntity.ok(SupplierMapper.toResponse(supplier));
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String country) {

        List<Supplier> suppliers;

        if (active != null && active) {
            suppliers = supplierService.getActiveSuppliers();
        } else if (country != null && !country.trim().isEmpty()) {
            suppliers = supplierService.getSuppliersByCountry(country);
        } else {
            suppliers = supplierService.getAllSuppliers();
        }

        List<SupplierResponse> responseList = suppliers.stream()
                .map(SupplierMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsBySupplier(@PathVariable UUID id) {
        List<ProductDto> products = supplierService.getProductsBySupplier(id);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable UUID id,
            @Valid @RequestBody SupplierRequest supplierDetails) {
        Supplier updated = supplierService.updateSupplier(id, supplierDetails);
        return ResponseEntity.ok(SupplierMapper.toResponse(updated));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<SupplierResponse> activateSupplier(@PathVariable UUID id) {
        Supplier activated = supplierService.activateSupplier(id);
        return ResponseEntity.ok(SupplierMapper.toResponse(activated));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<SupplierResponse> deactivateSupplier(@PathVariable UUID id) {
        Supplier deactivated = supplierService.deactivateSupplier(id);
        return ResponseEntity.ok(SupplierMapper.toResponse(deactivated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}