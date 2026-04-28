package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Client;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic.ClientRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.ClientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientValidation clientValidation;

    @Autowired
    private MessageUtils messageUtils;

    protected void clientExistsValidation(UUID id) {
        clientValidation.clientExistsValidation(id);
    }

    protected Client getClientById(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException(messageUtils.getMessage("client.not-exists")));
    }
}
