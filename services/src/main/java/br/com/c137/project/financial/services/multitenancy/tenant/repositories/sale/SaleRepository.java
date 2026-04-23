package br.com.c137.project.financial.services.multitenancy.tenant.repositories.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.Sale;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);

    <T> Optional<T> findById(UUID id, Class<T> type);

    @Transactional
    @Modifying
    @Query("UPDATE Sale s SET s.entityStatus = :entityStatus WHERE s.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);
}
