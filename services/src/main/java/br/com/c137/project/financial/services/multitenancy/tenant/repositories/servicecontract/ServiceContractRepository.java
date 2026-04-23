package br.com.c137.project.financial.services.multitenancy.tenant.repositories.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract.ServiceContractGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContract;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceContractRepository extends JpaRepository<ServiceContract, UUID> {
    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);

    <T> Optional<T> findById(UUID id, Class<T> type);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceContract sc SET sc.entityStatus = :entityStatus WHERE sc.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);
}
