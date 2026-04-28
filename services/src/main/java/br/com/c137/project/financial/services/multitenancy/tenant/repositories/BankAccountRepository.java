package br.com.c137.project.financial.services.multitenancy.tenant.repositories;

import br.com.c137.project.financial.services.multitenancy.tenant.enums.Bank;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.BankAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    <T> Optional<T> findById(UUID id, Class<T> type);

    <T> Page<T> findAllBy(Pageable pageable, Class<T> type);

    boolean existsByBankAndBranchNumberAndAccountNumberAndAccountDigit(
            Bank bank,
            String branchNumber,
            String accountNumber,
            String accountDigit
    );

    boolean existsByBankAndBranchNumberAndAccountNumberAndAccountDigitAndIdNot(
            Bank bank,
            String branchNumber,
            String accountNumber,
            String accountDigit,
            UUID id
    );

    boolean existsByPixKey(String pixKey);

    boolean existsByPixKeyAndIdNot(String pixKey, UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE BankAccount bc SET bc.entityStatus = :entityStatus WHERE bc.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);

    @Query("SELECT bc.name FROM BankAccount bc WHERE bc.id = :bankAccountId")
    String getBankAccountName(UUID bankAccountId);
}
