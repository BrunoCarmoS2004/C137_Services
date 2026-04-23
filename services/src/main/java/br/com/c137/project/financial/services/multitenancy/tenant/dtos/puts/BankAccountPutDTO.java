package br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.Bank;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.BankAccountEntityType;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.PixKeyType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BankAccountPutDTO(
        @NotBlank(message = "Account name is required")
        @Size(max = 100, message = "Account name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Account type is required")
        String accountType,

        @NotNull(message = "Entity type is required")
        BankAccountEntityType bankAccountEntityType,

        @NotNull(message = "Bank information is required")
        Bank bank,

        @NotBlank(message = "Branch number is required")
        @Pattern(regexp = "\\d{1,5}", message = "Branch number must contain only digits (max 5)")
        String branchNumber,

        @Size(max = 2, message = "Branch digit must not exceed 2 characters")
        String branchDigit,

        @NotBlank(message = "Account number is required")
        @Pattern(regexp = "\\d{1,12}", message = "Account number must contain only digits (max 12)")
        String accountNumber,

        @NotBlank(message = "Account digit is required")
        @Size(max = 2, message = "Account digit must not exceed 2 characters")
        String accountDigit,

        PixKeyType pixKeyType,

        String pixKey,

        @NotNull(message = "QR Code generation preference is required")
        Boolean generateQrCode,

        @NotNull(message = "Initial balance is required")
        @DecimalMin(value = "0.00", message = "Initial balance cannot be negative")
        BigDecimal initialBalance,

        @NotNull(message = "Initial balance date is required")
        @PastOrPresent(message = "Initial balance date cannot be in the future")
        LocalDate initialBalanceDate,

        @NotNull(message = "Slip issuance preference is required")
        Boolean issuesSlips
) {
}
