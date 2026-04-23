package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InstallmentGetDTO(
        UUID id,

        LocalDate dueDate,

        BigDecimal installmentAmount,

        String observation,

        EntityStatus entityStatus
) {
}
