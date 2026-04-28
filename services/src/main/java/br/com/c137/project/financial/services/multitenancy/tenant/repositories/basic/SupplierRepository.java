package br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic;

import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    <T> Optional<T> findById(UUID id, Class<T> type);

    @Query("SELECT s.name FROM Supplier s WHERE s.id = :supplierId")
    String getSupplierName(UUID supplierId);
}
