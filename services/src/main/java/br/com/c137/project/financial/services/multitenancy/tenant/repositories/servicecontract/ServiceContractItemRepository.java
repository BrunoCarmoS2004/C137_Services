package br.com.c137.project.financial.services.multitenancy.tenant.repositories.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContractItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceContractItemRepository extends JpaRepository<ServiceContractItem, UUID> {
}
