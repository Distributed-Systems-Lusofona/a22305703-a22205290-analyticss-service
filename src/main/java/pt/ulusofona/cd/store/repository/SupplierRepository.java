package pt.ulusofona.cd.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ulusofona.cd.store.model.Supplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    Optional<Supplier> findByEmail(String email);
    List<Supplier> findByIsActiveTrue();
    List<Supplier> findByCountry(String country);
    List<Supplier> findByCompanyNameContainingIgnoreCase(String companyName);

    @Query("SELECT s FROM Supplier s WHERE s.isActive = :active")
    List<Supplier> findByActiveStatus(@Param("active") boolean active);
}