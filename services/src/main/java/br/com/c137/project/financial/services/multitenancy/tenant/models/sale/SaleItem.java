package br.com.c137.project.financial.services.multitenancy.tenant.models.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.ItemStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.ServiceProduct;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.c137.project.financial.services.utils.ServiceUtils.getUserIdFromToken;

@Entity
@Table(name = "sale_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private Sale sale;

    @Column(name = "service_product_id")
    private UUID serviceProductId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal discount;

    private BigDecimal total;

    @Column(name = "apply_discount")
    private Boolean applyDiscount;

    private String obsevations;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    private ItemStatus itemStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status")
    private EntityStatus entityStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
        this.createdBy = getUserIdFromToken();
        this.total = calculateTotal();
    }
    //Has the same method in the ServiceContractItem
    protected BigDecimal calculateTotal() {
        BigDecimal totalWithoutDiscount = calculateTotalWithoutDiscount();
        BigDecimal discount = (this.discount == null) ? BigDecimal.ZERO : this.discount;
        return totalWithoutDiscount.subtract(discount);
    }

    protected BigDecimal calculateTotalWithoutDiscount() {
        BigDecimal unitPrice = (this.unitPrice  == null) ? BigDecimal.ZERO : this.unitPrice;
        BigDecimal quantity = (this.quantity == null) ? BigDecimal.ZERO : BigDecimal.valueOf(this.quantity);
        return unitPrice.multiply(quantity);
    }
}
