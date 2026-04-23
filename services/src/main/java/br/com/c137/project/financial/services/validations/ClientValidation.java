package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic.ClientRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientValidation {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void clientExistsValidation(UUID id){
        boolean exist = clientRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("client.not-exists"));
        }
    }
}
