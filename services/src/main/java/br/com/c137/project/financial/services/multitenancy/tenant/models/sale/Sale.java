package br.com.c137.project.financial.services.multitenancy.tenant.models.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.ItemStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.ReceiptType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.SaleStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.BankAccount;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.c137.project.financial.services.utils.ServiceUtils.getUserIdFromToken;

@Entity
@Table(name = "sales")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "sale_date")
    private LocalDate saleDate; 

    @Column(name = "competence")
    private LocalDate competence; 

    @Column(name = "send_nfse")
    private Boolean sendNfse; 

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<SaleItem> saleItems;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Installment> installments;

    @Column(name = "total_sale_gross_amount")
    private BigDecimal totalSaleGrossAmount;

    @Column(name = "total_services_invoicing_amount")
    private BigDecimal totalServicesInvoicingAmount;

    @Column(name = "total_services_invoicing_discount_amount")
    private BigDecimal totalServicesInvoicingDiscountAmount;

    @Column(name = "total_services_cancelled_amount")
    private BigDecimal totalServicesCancelledAmount;

    @Column(name = "total_services_free_amount")
    private BigDecimal totalServicesFreeAmount;

    @Column(name = "total_sale_net_amount")
    private BigDecimal totalSaleNetAmount;

    @Column(name = "with_Tax_withheld")
    private Boolean taxWithheld; 

    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "receipt_type")
    private ReceiptType receiptType; 

    @Column(name = "installment_Count")
    private Integer installmentCount; 

    @Column(name = "has_Down_Payment")
    private Boolean hasDownPayment;

    //TODO ARRUMAR ESSA QUESTÃO DA PARTE DO ATTACHMENTS, FAZER IGUAL AO SERVICE CONTRACT
    @Column(name = "attachments")
    private String attachments;

    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private SaleStatus saleStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt; 

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status")
    private EntityStatus entityStatus;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
        this.createdBy = getUserIdFromToken();
        this.installmentCount = this.installments.size();
        initTotals();
        calculateTotals();
    }

    private void initTotals() {
        this.totalSaleGrossAmount = BigDecimal.ZERO;
        this.totalServicesInvoicingAmount = BigDecimal.ZERO;
        this.totalServicesInvoicingDiscountAmount = BigDecimal.ZERO;
        this.totalServicesCancelledAmount = BigDecimal.ZERO;
        this.totalServicesFreeAmount = BigDecimal.ZERO;
        this.totalSaleNetAmount = BigDecimal.ZERO;
    }

    private void calculateTotals() {
        for (SaleItem item : saleItems) {
            BigDecimal gross = item.calculateTotalWithoutDiscount();
            BigDecimal discount = item.getDiscount();
            BigDecimal net = gross.subtract(discount);
            ItemStatus status = item.getItemStatus();

            this.totalSaleGrossAmount = this.totalSaleGrossAmount.add(gross);

            switch (status) {
                case INVOICING:
                    this.totalServicesInvoicingAmount = this.totalServicesInvoicingAmount.add(gross);
                    this.totalServicesInvoicingDiscountAmount = this.totalServicesInvoicingDiscountAmount.add(discount);
                    this.totalSaleNetAmount = this.totalSaleNetAmount.add(net);
                    break;
                case CANCELED:
                    this.totalServicesCancelledAmount = this.totalServicesCancelledAmount.add(net);
                    break;
                case FREE:
                    this.totalServicesFreeAmount = this.totalServicesFreeAmount.add(net);
                    break;
            }
        }
    }
}
