package br.com.c137.project.financial.services.multitenancy.tenant.repositories;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

    <T> Optional<T> findById(UUID id, Class<T> type);

    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);

    @Transactional
    @Modifying
    @Query("UPDATE Receipt r SET r.entityStatus = :entityStatus WHERE r.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);
}
