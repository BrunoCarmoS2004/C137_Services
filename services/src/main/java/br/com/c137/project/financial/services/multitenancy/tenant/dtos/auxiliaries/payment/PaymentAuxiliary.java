package br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries;

public record PaymentAuxiliary(
        String clientName,
        String bankAccountName
) {
}
