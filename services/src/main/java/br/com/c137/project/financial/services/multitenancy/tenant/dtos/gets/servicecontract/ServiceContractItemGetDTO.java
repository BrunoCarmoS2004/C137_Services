package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.ItemStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceContractItemGetDTO(
        UUID id,

        UUID serviceProductId,

        BigDecimal unitPrice,

        Integer quantity,

        BigDecimal discount,

        Boolean applyDiscount,

        String obsevations,

        ItemStatus itemStatus,

        EntityStatus entityStatus

) {
}
