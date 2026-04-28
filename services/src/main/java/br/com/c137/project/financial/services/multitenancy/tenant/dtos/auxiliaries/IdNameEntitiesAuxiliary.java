package br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries;

import lombok.NoArgsConstructor;

import java.util.UUID;

public record IdNameEntitiesAuxiliary(
        UUID id,
        String name
) {
}
