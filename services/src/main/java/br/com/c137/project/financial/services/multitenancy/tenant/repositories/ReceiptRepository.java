package br.com.c137.project.financial.services.multitenancy.tenant.repositories;

import br.com.c137.project.financial.services.multitenancy.tenant.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
}
