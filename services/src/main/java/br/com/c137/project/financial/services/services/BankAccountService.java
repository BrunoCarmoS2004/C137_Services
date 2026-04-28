package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.mappers.BankAccountMapper;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.BankAccountGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.BankAccountPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.BankAccountPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.BankAccount;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.BankAccountRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.BankAccountValidation;
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
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountValidation bankAccountValidation;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "bankaccounts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<BankAccountGetDTO> getAll(Pageable pageable) {
        Page<BankAccountGetDTO> bankAccounts = bankAccountRepository.findAllBy(pageable, BankAccountGetDTO.class);
        return new PagedModel<>(bankAccounts);
    }

    @Cacheable(value = "bankaccount", key = "#id")
    public BankAccountGetDTO getBankAccountById(UUID id) {
        return bankAccountRepository.findById(id, BankAccountGetDTO.class).orElseThrow(
                () -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "bankaccounts", allEntries = true)
    public BankAccountGetDTO postBankAccount(BankAccountPostDTO bankAccountPostDTO) {
        bankAccountValidation.bankAndBranchNumberAndAccountNumberAndAccountDigitExistsValidation(
                bankAccountPostDTO.bank(),
                bankAccountPostDTO.branchNumber(),
                bankAccountPostDTO.accountNumber(),
                bankAccountPostDTO.accountDigit());
        bankAccountValidation.pixKeyExistsValidation(bankAccountPostDTO.pixKey());
        BankAccount bankAccount = bankAccountMapper.postToBankAccount(bankAccountPostDTO);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.bankAccountToBankAccountGetDTO(bankAccount);
    }

    @Caching(evict = {
            @CacheEvict(value = "bankaccounts", allEntries = true),
            @CacheEvict(value = "bankaccount", key = "#id")
    })
    public BankAccountGetDTO putBankAccount(UUID id, BankAccountPutDTO bankAccountPutDTO) {
        bankAccountValidation.bankAndBranchNumberAndAccountNumberAndAccountDigitExistsInOtherIdValidation(
                bankAccountPutDTO.bank(),
                bankAccountPutDTO.branchNumber(),
                bankAccountPutDTO.accountNumber(),
                bankAccountPutDTO.accountDigit(),
                id);
        bankAccountValidation.pixKeyExistsInOtherIdValidation(bankAccountPutDTO.pixKey(), id);
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        bankAccount =  bankAccountMapper.putToBankAccount(bankAccountPutDTO, bankAccount);
        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.bankAccountToBankAccountGetDTO(bankAccount);
    }

    @Caching(evict = {
            @CacheEvict(value = "bankaccounts", allEntries = true),
            @CacheEvict(value = "bankaccount", key = "#id")
    })
    public void deleteBankAccount(UUID id) {
        bankAccountExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "bankaccounts", allEntries = true),
            @CacheEvict(value = "bankaccount", key = "#id")
    })
    public void inactiveBankAccount(UUID id) {
        bankAccountExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void bankAccountExistsValidation(UUID id){
        bankAccountValidation.bankAccountExistsValidation(id);
    }

    protected IdNameEntitiesAuxiliary getIdNameBankAccount(UUID id){
        return bankAccountRepository.findById(id, IdNameEntitiesAuxiliary.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        bankAccountRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage(){
        return messageUtils.getMessage("bank.account.not-found");
    }

    public String getBankAccountName(UUID bankAccountId) {
        return bankAccountRepository.getBankAccountName(bankAccountId);
    }
}
