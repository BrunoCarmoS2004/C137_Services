package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic.ServiceProductRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class ServiceProductValidation {
    @Autowired
    private ServiceProductRepository serviceProductRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void serviceProductExistsValidation(UUID id){
        boolean exists = serviceProductRepository.existsById(id);
        if(!exists){
            throw new NotFoundException(messageUtils.getMessage("service.product.not-exists"));
        }
    }

    public Set<UUID> serviceProductExistsByIdsValidation(Set<UUID> ids){
        Set<UUID> existsIds = serviceProductRepository.findExistsByIds(ids);
        if(existsIds.isEmpty()){
            throw new NotFoundException(messageUtils.getMessage("service.product.ids.none-found"));
        }
        return existsIds;
    }

}
