package br.com.c137.project.financial.services.multitenancy.tenant.models;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.Bank;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.BankAccountEntityType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.PixKeyType;
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
@Table(name = "bank_accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"bank", "branch_number", "account_number", "account_digit"})
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "account_type")
    private String accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_account_entity_type")
    private BankAccountEntityType bankAccountEntityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank")
    private Bank bank;

    @Column(name = "branch_number")
    private String branchNumber;

    @Column(name = "branch_digit")
    private String branchDigit;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_digit")
    private String accountDigit;

    @Enumerated(EnumType.STRING)
    @Column(name = "pix_key_type")
    private PixKeyType pixKeyType;

    @Column(name = "pix_key")
    private String pixKey;

    @Column(name = "generate_qr_code")
    private Boolean generateQrCode;

    @Column(name = "initial_balance")
    private BigDecimal initialBalance;

    @Column(name = "initial_balance_date")
    private LocalDate initialBalanceDate;

    @Column(name = "issues_slips")
    private Boolean issuesSlips;

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
