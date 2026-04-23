package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.servicecontract.ServiceContractRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServiceContractValidation {
    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void serviceContractExistsValidation(UUID id) {
        boolean exists = serviceContractRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("service.contract.not-exists"));
        }
    }
}
