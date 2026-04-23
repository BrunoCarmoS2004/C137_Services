package br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic;

import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
