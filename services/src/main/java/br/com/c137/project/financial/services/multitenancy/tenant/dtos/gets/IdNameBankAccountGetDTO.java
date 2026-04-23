package br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets;

import java.util.UUID;

public record IdNameBankAccountGetDTO(
        UUID id,
        String name
) {
}
