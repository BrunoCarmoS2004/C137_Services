package br.com.c137.project.financial.services.multitenancy.tenant.models.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "installments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private Sale sale;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "installment_amount")
    private BigDecimal installmentAmount;

    private String observation;

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
    }
}
