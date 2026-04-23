package br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.InstallmentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SaleItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.ReceiptType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.SaleStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SalePutDTO(
        @NotNull(message = "Client ID is required")
        UUID clientId,

        @NotNull(message = "Sale date is required")
        @PastOrPresent(message = "Sale date cannot be in the future")
        LocalDate saleDate,

        @NotNull(message = "Competence date is required")
        LocalDate competence,

        @NotNull(message = "NFSe delivery preference is required")
        Boolean sendNfse,

        @NotEmpty(message = "Sale must have at least one item")
        List<SaleItemPostDTO> saleItems,

        List<InstallmentPostDTO> installments,

        @NotNull(message = "Tax withheld flag is required")
        Boolean taxWithheld,

        @NotNull(message = "Bank account ID is required")
        UUID bankAccountId,

        @NotNull(message = "Receipt type is required")
        ReceiptType receiptType,

        Integer installmentCount,

        @NotNull(message = "Down payment flag is required")
        Boolean hasDownPayment,

        String attachments,

        @Size(max = 1000, message = "Observation is too long")
        String observation,

        @NotNull(message = "Sale status is required")
        SaleStatus saleStatus

) {

}
