package br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.servicecontract;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;

public record ServiceContractAuxiliary(
        IdNameEntitiesAuxiliary clientInfo,
        IdNameEntitiesAuxiliary bankAccountInfo
) {
}
