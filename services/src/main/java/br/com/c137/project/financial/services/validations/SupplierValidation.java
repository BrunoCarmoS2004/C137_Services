package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic.SupplierRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplierValidation {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void supplierExistsValidation(UUID id){
        boolean exist = supplierRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("supplier.not-exists"));
        }
    }
}
