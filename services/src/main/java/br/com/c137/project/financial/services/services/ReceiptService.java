package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.mappers.ReceiptMapper;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.receipt.ReceiptAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.ReceiptGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.ReceiptPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.ReceiptPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Receipt;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.ReceiptRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.ReceiptValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptValidation receiptValidation;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ClientService clientService;

    @Cacheable(value = "receipts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<ReceiptGetDTO> getAll(Pageable pageable) {
        Page<ReceiptGetDTO> receipts = receiptRepository.findAllBy(pageable, ReceiptGetDTO.class);
        return new PagedModel<>(receipts);
    }

    @Cacheable(value = "receipt", key = "#id")
    public ReceiptGetDTO getReceiptById(UUID id) {
        return receiptRepository.findById(id, ReceiptGetDTO.class).orElseThrow(
                () -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "receipts", allEntries = true)
    public ReceiptGetDTO postReceipt(ReceiptPostDTO receiptPostDTO) {
        ReceiptAuxiliary receiptAuxiliary = resolveReceiptDependencies(
                receiptPostDTO.categoryId(),
                receiptPostDTO.bankAccountId(),
                receiptPostDTO.clientId()
        );
        Receipt receipt = receiptMapper.postToReceipt(receiptPostDTO);
        return saveAndReturn(receipt, receiptAuxiliary);
    }

    @Caching(evict = {
            @CacheEvict(value = "receipts", allEntries = true),
            @CacheEvict(value = "receipt", key = "#id")
    })
    public ReceiptGetDTO putReceipt(UUID id, ReceiptPutDTO receiptPutDTO) {
        ReceiptAuxiliary receiptAuxiliary = resolveReceiptDependencies(
                receiptPutDTO.categoryId(),
                receiptPutDTO.bankAccountId(),
                receiptPutDTO.clientId()
        );
        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        receipt = receiptMapper.putToReceipt(receiptPutDTO, receipt);
        return saveAndReturn(receipt, receiptAuxiliary);
    }

    @Caching(evict = {
            @CacheEvict(value = "receipts", allEntries = true),
            @CacheEvict(value = "receipt", key = "#id")
    })
    public void deleteReceipt(UUID id) {
        receiptExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "receipts", allEntries = true),
            @CacheEvict(value = "receipt", key = "#id")
    })
    public void inactiveReceipt(UUID id) {
        receiptExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void receiptExistsValidation(UUID id) {
        receiptValidation.receiptExistsValidation(id);
    }


    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        receiptRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage() {
        return messageUtils.getMessage("receipt.not-found");
    }

    private ReceiptAuxiliary resolveReceiptDependencies(
            UUID categoryId,
            UUID bankAccountId,
            UUID clientId
    ) {
        //Validations if exists in services
        IdNameEntitiesAuxiliary idNameCategory = categoryService.getIdNameCategory(categoryId);
        IdNameEntitiesAuxiliary idNameBankAccount = bankAccountService.getIdNameBankAccount(bankAccountId);
        IdNameEntitiesAuxiliary idNameClient = 
                clientId != null
                ? clientService.getIdNameClient(clientId) : new IdNameEntitiesAuxiliary(null, null);
        //CLIENT, BANK ACCOUNT, CATEGORY
        return new ReceiptAuxiliary(idNameClient, idNameBankAccount, idNameCategory);
    }

    private ReceiptGetDTO saveAndReturn(Receipt receipt, ReceiptAuxiliary receiptAuxiliary) {
        receipt.setClientName(receiptAuxiliary.clientInfo().name());
        receipt.setBankAccountName(receiptAuxiliary.bankAccountInfo().name());
        receipt.setCategoryName(receiptAuxiliary.categoryInfo().name());
        receipt = receiptRepository.save(receipt);
        return receiptMapper.receiptToReceiptGetDTO(receipt);
    }
}
