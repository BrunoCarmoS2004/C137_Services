package br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.PaymentType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.TransactionStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceiptPostDTO(
        @NotBlank(message = "Description is required")
        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,

        UUID clientId,

        @NotNull(message = "Category Id is required")
        UUID categoryId,

        @NotNull(message = "Transaction status is required")
        TransactionStatus status,

        @NotNull(message = "Bank account ID is required")
        UUID bankAccountId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Due date is required")
        LocalDate dueDate,

        @NotNull(message = "Payment flag (isPaid) is required")
        Boolean isPaid,

        LocalDate paymentDate,

        @DecimalMin(value = "0.00", message = "Discounts/Fees cannot be negative")
        BigDecimal discountsFees,

        @DecimalMin(value = "0.00", message = "Penalty/Interest cannot be negative")
        BigDecimal penaltyInterest,

        @NotNull(message = "Total received amount is required")
        @DecimalMin(value = "0.00", message = "Total received cannot be negative")
        BigDecimal totalReceived,

        @NotNull(message = "Payment type is required")
        PaymentType paymentType,

        @NotNull(message = "Accrual date (competence) is required")
        LocalDate accrualDate,

        @Size(max = 100, message = "Cost center name is too long")
        String costCenter,

        @Size(max = 50, message = "Document number is too long")
        String documentNumber,

        @Size(max = 1000, message = "Observation is too long")
        String observation
) {


}
