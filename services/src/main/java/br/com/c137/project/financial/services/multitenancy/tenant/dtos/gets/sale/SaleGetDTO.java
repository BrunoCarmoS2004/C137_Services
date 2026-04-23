package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.InstallmentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SaleItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.ReceiptType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.SaleStatus;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SaleGetDTO(
        UUID id,

        UUID clientId,

        LocalDate saleDate,

        LocalDate competence,

        Boolean sendNfse,

        List<SaleItemGetDTO> saleItems,

        List<InstallmentGetDTO> installments,

        BigDecimal totalSaleGrossAmount,

        BigDecimal totalServicesInvoicingAmount,

        BigDecimal totalServicesInvoicingDiscountAmount,

        BigDecimal totalServicesCancelledAmount,

        BigDecimal totalServicesFreeAmount,

        BigDecimal totalSaleNetAmount,

        Boolean taxWithheld,

        UUID bankAccountId,

        ReceiptType receiptType,

        Integer installmentCount,

        Boolean hasDownPayment,

        String attachments,

        String observation,

        SaleStatus saleStatus,

        EntityStatus entityStatus

) {

}
