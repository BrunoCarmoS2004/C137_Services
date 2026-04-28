package br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.sale;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;

public record SaleAuxiliary(
        IdNameEntitiesAuxiliary clientInfo,
        IdNameEntitiesAuxiliary bankAccountInfo
) {
}
