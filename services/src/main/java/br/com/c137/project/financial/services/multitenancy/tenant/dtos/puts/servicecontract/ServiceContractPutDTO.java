package br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ServiceContractPutDTO(
        @NotNull(message = "Client ID is required")
        UUID clientId,

        @NotNull(message = "Contract type is required")
        ContractType contractType,

        @NotNull(message = "Contract status is required")
        ContractStatus contractStatus,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "Adjustment rule is required")
        Adjustment adjustment,

        @Min(value = 0, message = "Notification days cannot be negative")
        Integer notifyAdjustment,

        @DecimalMin(value = "0.00", message = "Adjustment index cannot be negative")
        BigDecimal adjustmentIndex,

        LocalDate monthAdjustment,

        @NotNull(message = "Signed contract status is required")
        Boolean signedContract,

        @Size(max = 150, message = "Signed by name is too long")
        String signedBy,

        @NotNull(message = "Automatic invoice preference is required")
        Boolean invoiceAutomatically,

        @NotNull(message = "NFSe delivery preference is required")
        Boolean sendNfse,

        @NotNull(message = "Extra month billing rule is required")
        BillExtraMonth billExtraMonth,

        @NotNull(message = "Billing model is required")
        BillModel billingModel,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Bank account ID is required")
        UUID bankAccountId,

        @NotNull(message = "Custom partnership discount status is required")
        Boolean hasCustomPartnershipDiscount,

        @NotEmpty(message = "At least one service or product must be selected")
        List<ServiceContractItemPutDTO> serviceContractItems,

        @NotNull(message = "PIS retention status is required")
        Boolean retainAliquotPis,

        @NotNull(message = "IR retention status is required")
        Boolean retainAliquotIr,

        @NotNull(message = "COFINS retention status is required")
        Boolean retainAliquotConfins,

        @NotNull(message = "INSS retention status is required")
        Boolean retainAliquotInss,

        @NotNull(message = "CSLL retention status is required")
        Boolean retainAliquotCsll,

        String attachmentName,

        @Size(max = 1000, message = "Observation text is too long")
        String observation
) {
}
