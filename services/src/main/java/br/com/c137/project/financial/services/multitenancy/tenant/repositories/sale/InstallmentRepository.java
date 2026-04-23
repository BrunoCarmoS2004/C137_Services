package br.com.c137.project.financial.services.multitenancy.tenant.repositories.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, UUID> {
}
