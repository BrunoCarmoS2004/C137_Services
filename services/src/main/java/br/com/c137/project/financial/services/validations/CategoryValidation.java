package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.CategoryRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryValidation {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void categoryExistsValidation(UUID id) {
        boolean exists = categoryRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("category.not-exists"));
        }
    }

    public void nameExistsValidation(String name) {
        boolean exists = categoryRepository.existsByName(name);
        if (exists) {
            throw new NotFoundException(getCategoryExistMessage());
        }
    }

    public void nameExistsInOtherIdValidation(String name, UUID id) {
        boolean exists = categoryRepository.existsByNameAndIdNot(name, id);
        if (exists) {
            throw new NotFoundException(getCategoryExistMessage());
        }
    }

    private String getCategoryExistMessage(){
        return messageUtils.getMessage("category.exists");
    }
}
