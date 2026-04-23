package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.Bank;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.BankAccountEntityType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.PixKeyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BankAccountGetDTO(
        UUID id,

        String name,

        String accountType,

        BankAccountEntityType bankAccountEntityType,

        Bank bank,

        String branchNumber,

        String branchDigit,

        String accountNumber,

        String accountDigit,

        PixKeyType pixKeyType,

        String pixKey,

        Boolean generateQrCode,

        BigDecimal initialBalance,

        LocalDate initialBalanceDate,

        Boolean issuesSlips,

        EntityStatus entityStatus
) {
}
