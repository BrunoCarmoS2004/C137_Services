package br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.PaymentType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.TransactionStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentPutDTO(
        @NotBlank(message = "Description is required")
        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description,

        UUID supplierId,

        @NotNull(message = "Category Id is required")
        UUID categoryId,

        @NotNull(message = "TransactionStatus is required")
        TransactionStatus transactionStatus,

        @NotNull(message = "Bank account ID is required")
        UUID bankAccountId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Due date is required")
        LocalDate dueDate,

        @NotNull(message = "Payment flag (isPaid) is required")
        Boolean isPaid,

        @NotNull(message = "Payment date is required")
        LocalDate paymentDate,

        @DecimalMin(value = "0.00", message = "Discounts/Fees cannot be negative")
        BigDecimal discountsFees,

        @DecimalMin(value = "0.00", message = "Penalty/Interest cannot be negative")
        BigDecimal penaltyInterest,

        @DecimalMin(value = "0.00", message = "Total paid cannot be negative")
        BigDecimal totalPaid,

        @NotNull(message = "Payment type is required")
        PaymentType paymentType,

        @NotNull(message = "Competence date is required")
        LocalDate competenceDate,

        @Size(max = 100, message = "Cost center name is too long")
        String costCenter,

        @Size(max = 50, message = "Document number is too long")
        String documentNumber,

        @Size(max = 1000, message = "Observation is too long")
        String observation
) {
}
