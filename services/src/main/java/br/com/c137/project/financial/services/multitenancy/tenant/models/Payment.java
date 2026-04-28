package br.com.c137.project.financial.services.multitenancy.tenant.models;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.PaymentType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.TransactionStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.c137.project.financial.services.utils.ServiceUtils.getUserIdFromToken;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "discounts_fees")
    private BigDecimal discountsFees;

    @Column(name = "penalty_interest")
    private BigDecimal penaltyInterest;

    @Column(name = "total_paid")
    private BigDecimal totalPaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "competence_date")
    private LocalDate competenceDate;

    @Column(name = "cost_center")
    private String costCenter;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "observation")
    private String observation;

    @Column(name = "supplier_id")
    private UUID supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status", nullable = false)
    private EntityStatus entityStatus;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
        this.createdBy = getUserIdFromToken();
    }
}
