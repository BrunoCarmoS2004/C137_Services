package br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.payment;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;

public record PaymentAuxiliary(
        IdNameEntitiesAuxiliary supplierInfo,
        IdNameEntitiesAuxiliary bankAccountInfo,
        IdNameEntitiesAuxiliary categoryInfo
) {
}
