package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.sale.SaleRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SaleValidation {
    
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private MessageUtils messageUtils;
    
    public void saleExistsValidation(UUID id) {
        boolean exists = saleRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("sale.not-exists"));
        }
    }
    
}
