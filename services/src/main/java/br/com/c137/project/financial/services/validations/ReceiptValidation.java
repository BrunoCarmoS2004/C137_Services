package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.PaymentRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentValidation {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void paymentExistsValidation(UUID id){
        boolean exist = paymentRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("payment.not-exists"));
        }
    }
}
