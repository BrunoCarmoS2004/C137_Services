package br.com.c137.project.financial.services.multitenancy.tenant.repositories;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;
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
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    <T> Optional<T> findById(UUID id, Class<T> type);

    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.entityStatus = :entityStatus WHERE c.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);
}
