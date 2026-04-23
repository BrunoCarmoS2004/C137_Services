package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.PaymentType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.TransactionStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentGetDTO(
        UUID id,

        String description,

        Category category,

        TransactionStatus transactionStatus,

        UUID bankAccountId,

        String bankAccountName,

        BigDecimal amount,

        LocalDate dueDate,

        Boolean isPaid,

        LocalDate paymentDate,

        BigDecimal discountsFees,

        BigDecimal penaltyInterest,

        BigDecimal totalPaid,

        PaymentType paymentType,

        LocalDate competenceDate,

        String costCenter,

        String documentNumber,

        String observation,

        UUID supplierId,

        String supplierName,

        EntityStatus entityStatus
) {
}
