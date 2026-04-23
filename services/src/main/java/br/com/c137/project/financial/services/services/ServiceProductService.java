package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.ServiceProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ServiceProductService {
    @Autowired
    private ServiceProductValidation serviceProductValidation;

    @Autowired
    private MessageUtils messageUtils;

    public void serviceProcuctExistsValidation(UUID id){
        serviceProductValidation.serviceProductExistsValidation(id);
    }

    public void serviceProductExistsByIdsValidation(Set<UUID> ids){
        ids.removeAll(serviceProductValidation.serviceProductExistsByIdsValidation(ids));
        if (!ids.isEmpty()) {
            throw new NotFoundException(messageUtils.getIdsMessage("service.product.ids.not-exists", ids));
        }
    }
}
