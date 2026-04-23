package br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.ItemStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemPutDTO(

        @NotNull(message = "Sale Item ID is required")
        UUID id,

        @NotNull(message = "Service or Product ID is required")
        UUID serviceProductId,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.00", message = "Unit price cannot be negative")
        BigDecimal unitPrice,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity,

        @NotNull(message = "Discount value is required")
        @DecimalMin(value = "0.00", message = "Discount cannot be negative")
        BigDecimal discount,

        @NotNull(message = "Apply discount flag is required")
        Boolean applyDiscount,

        @Size(max = 500, message = "Observations must not exceed 500 characters")
        String obsevations,

        @NotNull(message = "Item status is required")
        ItemStatus itemStatus
) {

}
