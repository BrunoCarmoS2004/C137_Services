package br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InstallmentPostDTO(
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
