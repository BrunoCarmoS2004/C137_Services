package br.com.c137.project.financial.services.validations;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.Bank;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.BankAccountRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BankAccountValidation {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MessageUtils  messageUtils;

    public void bankAccountExistsValidation(UUID id) {
        boolean exists = bankAccountRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("bank.account.not-exists"));
        }
    }

    public void bankAndBranchNumberAndAccountNumberAndAccountDigitExistsValidation(
            Bank bank,
            String branchNumber,
            String accountNumber,
            String accountDigit) {
        boolean exists = bankAccountRepository.existsByBankAndBranchNumberAndAccountNumberAndAccountDigit(bank, branchNumber, accountNumber, accountDigit);
        if (exists) {
            throw new NotFoundException(getBankAndBranchNumberAndAccountNumberAndAccountDigitExistMessage());
        }
    }

    public void bankAndBranchNumberAndAccountNumberAndAccountDigitExistsInOtherIdValidation(
            Bank bank,
            String branchNumber,
            String accountNumber,
            String accountDigit,
            UUID id) {
        boolean exists = bankAccountRepository.existsByBankAndBranchNumberAndAccountNumberAndAccountDigitAndIdNot(bank, branchNumber, accountNumber, accountDigit, id);
        if (exists) {
            throw new NotFoundException(getBankAndBranchNumberAndAccountNumberAndAccountDigitExistMessage());
        }
    }

    public void pixKeyExistsValidation(
            String pixKey) {
        boolean exists = bankAccountRepository.existsByPixKey(pixKey);
        if (exists) {
            throw new NotFoundException(getPixKeyExistMessage());
        }
    }

    public void pixKeyExistsInOtherIdValidation(
            String pixKey,
            UUID id) {
        boolean exists = bankAccountRepository.existsByPixKeyAndIdNot(pixKey, id);
        if (exists) {
            throw new NotFoundException(getPixKeyExistMessage());
        }
    }

    private String getBankAndBranchNumberAndAccountNumberAndAccountDigitExistMessage(){
        return messageUtils.getMessage("bank.account.bn.an.ad.exists");
    }

    private String getPixKeyExistMessage(){
        return messageUtils.getMessage("bank.account.pix-key.exists");
    }

}
