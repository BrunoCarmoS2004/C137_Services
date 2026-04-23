package br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic;

import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
