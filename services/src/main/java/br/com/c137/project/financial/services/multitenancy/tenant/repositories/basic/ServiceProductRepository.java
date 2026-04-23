package br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic;

import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.ServiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ServiceProductRepository extends JpaRepository<ServiceProduct, UUID> {

    @Query("SELECT sp.id FROM ServiceProduct sp WHERE sp.id IN :ids")
    Set<UUID> findExistsByIds(Set<UUID> ids);

}
