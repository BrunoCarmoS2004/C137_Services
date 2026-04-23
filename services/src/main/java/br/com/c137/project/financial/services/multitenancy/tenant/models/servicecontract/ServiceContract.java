package br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.*;
import br.com.c137.project.financial.services.multitenancy.tenant.models.BankAccount;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "services_contracts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceContract {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "client_name")
    private String clientName;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status")
    private ContractStatus contractStatus;

    @Column(name = "start_date")
    private LocalDate startDate; 

    @Enumerated(EnumType.STRING)
    @Column(name = "adjustment")
    private Adjustment adjustment;

    @Column(name = "notify_adjustment")
    private Integer notifyAdjustment; 

    @Column(name = "adjustment_index")
    private BigDecimal adjustmentIndex; 

    @Column(name = "month_adjustment")
    private LocalDate monthAdjustment; 

    @Column(name = "signed_contract")
    private Boolean signedContract; 

    @Column(name = "signed_by")
    private String signedBy; 

    @Column(name = "created_at")
    private LocalDateTime createdAt; 

    @Column(name = "invoice_automatically")
    private Boolean invoiceAutomatically; 

    @Column(name = "send_nfse")
    private Boolean sendNfse; 

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status")
    private EntityStatus entityStatus; 

    @Enumerated(EnumType.STRING)
    @Column(name = "bill_extra_month")
    private BillExtraMonth billExtraMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_model")
    private BillModel billingModel; 

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "has_custom_partnership_discount")
    private Boolean hasCustomPartnershipDiscount;

    @OneToMany(mappedBy = "serviceContract", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ServiceContractItem> serviceContractItems;

    @Column(name = "total_contract_gross_amount")
    private BigDecimal totalContractGrossAmount;

    @Column(name = "total_services_invoicing_amount")
    private BigDecimal totalServicesInvoicingAmount;

    @Column(name = "total_services_invoicing_discount_amount")
    private BigDecimal totalServicesInvoicingDiscountAmount;

    @Column(name = "total_services_cancelled_amount")
    private BigDecimal totalServicesCancelledAmount;

    @Column(name = "total_services_free_amount")
    private BigDecimal totalServicesFreeAmount;

    @Column(name = "total_contract_net_amount")
    private BigDecimal totalContractNetAmount;

    @Column(name = "retain_aliquot_pis")
    private Boolean retainAliquotPis; 

    @Column(name = "retain_aliquot_ir")
    private Boolean retainAliquotIr; 

    @Column(name = "retain_aliquot_confins")
    private Boolean retainAliquotConfins; 

    @Column(name = "retain_aliquot_inss")
    private Boolean retainAliquotInss; 

    @Column(name = "retain_aliquot_csll")
    private Boolean retainAliquotCsll; 

    @Column(name = "attachment_link")
    private String attachmentLink;

    @Column(name = "attachment_name")
    private String attachmentName;

    private String observation;

    @Column(name = "last_billing_at")
    private LocalDateTime lastBillingAt;

    @Column(name = "is_billed")
    private Boolean isBilled;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
        this.createdBy = getUserIdFromToken();

        this.totalContractGrossAmount = BigDecimal.ZERO;
        this.totalServicesInvoicingAmount = BigDecimal.ZERO;
        this.totalServicesInvoicingDiscountAmount = BigDecimal.ZERO;
        this.totalServicesCancelledAmount = BigDecimal.ZERO;
        this.totalServicesFreeAmount = BigDecimal.ZERO;
        this.totalContractNetAmount = BigDecimal.ZERO;

        for (ServiceContractItem item : serviceContractItems) {
            BigDecimal gross = item.calculateTotalWithoutDiscount();
            BigDecimal discount = item.getDiscount();
            BigDecimal net = gross.subtract(discount);
            ItemStatus status = item.getItemStatus();

            this.totalContractGrossAmount = this.totalContractGrossAmount.add(gross);

            switch (status) {
                case INVOICING:
                    this.totalServicesInvoicingAmount = this.totalServicesInvoicingAmount.add(gross);
                    this.totalServicesInvoicingDiscountAmount = this.totalServicesInvoicingDiscountAmount.add(discount);
                    this.totalContractNetAmount = this.totalContractNetAmount.add(net);
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
