package br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.receipt;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;

public record ReceiptAuxiliary(
        IdNameEntitiesAuxiliary clientInfo,
        IdNameEntitiesAuxiliary bankAccountInfo,
        IdNameEntitiesAuxiliary categoryInfo
) {
}
