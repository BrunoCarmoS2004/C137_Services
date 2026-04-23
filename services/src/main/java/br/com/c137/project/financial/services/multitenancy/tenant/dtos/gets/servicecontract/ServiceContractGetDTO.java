package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.*;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ServiceContractGetDTO(
        UUID id,

        UUID clientId,

        String clientName,

        ContractType contractType,

        ContractStatus contractStatus,

        LocalDate startDate,

        Adjustment adjustment,

        Integer notifyAdjustment,

        BigDecimal adjustmentIndex,

        LocalDate monthAdjustment,

        Boolean signedContract,

        String signedBy,

        Boolean invoiceAutomatically,

        Boolean sendNfse,

        BillExtraMonth billExtraMonth,

        BillModel billingModel,

        PaymentMethod paymentMethod,

        UUID bankAccountId,

        String bankAccountName,

        Boolean hasCustomPartnershipDiscount,

        List<ServiceContractItemGetDTO> serviceContractItems,

        BigDecimal totalContractGrossAmount,

        BigDecimal totalServicesInvoicingAmount,

        BigDecimal totalServicesInvoicingDiscountAmount,

        BigDecimal totalServicesCancelledAmount,

        BigDecimal totalServicesFreeAmount,

        BigDecimal totalContractNetAmount,

        Boolean retainAliquotPis,

        Boolean retainAliquotIr,

        Boolean retainAliquotConfins,

        Boolean retainAliquotInss,

        Boolean retainAliquotCsll,

        String attachmentLink,

        String attachmentName,

        String observation
) {
}
