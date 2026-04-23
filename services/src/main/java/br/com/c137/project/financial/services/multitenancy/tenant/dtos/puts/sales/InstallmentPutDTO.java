package br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InstallmentPutDTO(
        @NotNull(message = "Installment ID is required")
        UUID id,

        @NotNull(message = "Due date is required")
        @FutureOrPresent(message = "Due date must be today or in the future")
        LocalDate dueDate,

        @NotNull(message = "Installment amount is required")
        @DecimalMin(value = "0.01", message = "Installment amount must be greater than zero")
        BigDecimal installmentAmount,

        @Size(max = 255, message = "Observation is too long")
        String observation
) {
}
