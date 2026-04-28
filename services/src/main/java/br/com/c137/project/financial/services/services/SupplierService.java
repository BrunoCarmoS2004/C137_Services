package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Supplier;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.basic.SupplierRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.SupplierValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierValidation supplierValidation;

    @Autowired
    private MessageUtils messageUtils;

    protected void supplierExistsValidation(UUID id) {
        supplierValidation.supplierExistsValidation(id);
    }

    protected Supplier getSupplierById(UUID id) {
        return supplierRepository.findById(id).orElseThrow(() -> new NotFoundException(messageUtils.getMessage("supplier.not-exists")));
    }

    public IdNameEntitiesAuxiliary getIdNameSupplier(UUID supplierId) {
        return supplierRepository.findById(supplierId, IdNameEntitiesAuxiliary.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }
    private String getNotFoundMessage(){
        return messageUtils.getMessage("supplier.not-found");
    }


}
