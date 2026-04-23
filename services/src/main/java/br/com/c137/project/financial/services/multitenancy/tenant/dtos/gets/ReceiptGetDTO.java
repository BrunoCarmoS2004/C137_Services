package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.PaymentType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.TransactionStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceiptGetDTO(
        UUID id,

        String description,

        Category category,

        TransactionStatus status,

        UUID bankAccountId,

        String bankAccountName,

        BigDecimal amount,

        LocalDate dueDate,

        Boolean isPaid,

        LocalDate paymentDate,

        BigDecimal discountsFees,

        BigDecimal penaltyInterest,

        BigDecimal totalReceived,

        PaymentType paymentType,

        UUID clientId,

        String clientName,

        LocalDate accrualDate,

        String costCenter,

        String documentNumber,

        String observation
) {


}
