package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.ReceiptRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ReceiptValidation {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void receiptExistsValidation(UUID id){
        boolean exist = receiptRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("receipt.not-exists"));
        }
    }
}
