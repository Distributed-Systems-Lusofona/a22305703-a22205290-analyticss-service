package pt.ulusofona.cd.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.store.client.ProductClient;
import pt.ulusofona.cd.store.dto.ProductDto;
import pt.ulusofona.cd.store.dto.SupplierRequest;
import pt.ulusofona.cd.store.events.SupplierEventProducer;
import pt.ulusofona.cd.store.exception.SupplierNotFoundException;
import pt.ulusofona.cd.store.mapper.SupplierMapper;
import pt.ulusofona.cd.store.model.Supplier;
import pt.ulusofona.cd.store.repository.SupplierRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductClient productClient;

    private final SupplierEventProducer supplierEventProducer;

    @Transactional
    public Supplier createSupplier(SupplierRequest request) {
        // Check if email already exists
        if (supplierRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
        Supplier supplier = SupplierMapper.toEntity(request);
        return supplierRepository.save(supplier);
    }

    public Supplier getSupplierById(UUID id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + id));
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findByIsActiveTrue();
    }

    public List<Supplier> getSuppliersByCountry(String country) {
        return supplierRepository.findByCountry(country);
    }

    @Transactional
    public Supplier updateSupplier(UUID id, SupplierRequest supplierDetails) {
        Supplier supplier = getSupplierById(id);

        supplier.setCompanyName(supplierDetails.getCompanyName());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setAddress(supplierDetails.getAddress());
        supplier.setPhoneNumber(supplierDetails.getPhoneNumber());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setTaxNumber(supplierDetails.getTaxNumber());
        supplier.setCountry(supplierDetails.getCountry());
        supplier.setCity(supplierDetails.getCity());
        supplier.setEstablishedDate(supplierDetails.getEstablishedDate());

        return supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier activateSupplier(UUID id) {
        Supplier supplier = getSupplierById(id);
        supplier.setActive(true);
        return supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier deactivateSupplier(UUID id) {
        Supplier supplier = getSupplierById(id);
        supplier.setActive(false);

        Supplier supplierUpdate = supplierRepository.save(supplier);

        supplierEventProducer.sendSupplierDeactivatedEvent(supplier.getId());

        return supplierUpdate;
    }

    @Transactional
    public void deleteSupplier(UUID id) {
        Supplier supplier = getSupplierById(id);
        supplierRepository.delete(supplier);
    }

    public List<ProductDto> getProductsBySupplier(UUID supplierId) {
        try {
            return productClient.getProductsBySupplier(supplierId.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products for supplier: " + e.getMessage());
        }
    }
}