package br.com.c137.project.financial.services.controllers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.BankAccountGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.BankAccountPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.BankAccountPutDTO;
import br.com.c137.project.financial.services.responses.ResponsePayload;
import br.com.c137.project.financial.services.services.BankAccountService;
import br.com.c137.project.financial.services.utils.MessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.c137.project.financial.services.utils.ServiceUtils.createResponse;

@RestController
@RequestMapping("/bankaccount")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;
    
    @Autowired
    private MessageUtils  messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<BankAccountGetDTO>> getAll(
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(bankAccountService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<BankAccountGetDTO>> getBankAccountById(@PathVariable UUID id) {
        BankAccountGetDTO bankAccountGetDTO = bankAccountService.getBankAccountById(id);
        return createResponse(
                HttpStatus.OK, 
                bankAccountGetDTO.id(), 
                bankAccountGetDTO,
                messageUtils.getMessage("bank.account.found"));
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<BankAccountGetDTO>> postBankAccount(@Valid @RequestBody BankAccountPostDTO bankPostDTO) {
        BankAccountGetDTO bankAccountGetDTO = bankAccountService.postBankAccount(bankPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                bankAccountGetDTO.id(),
                bankAccountGetDTO,
                messageUtils.getMessage("bank.account.created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<BankAccountGetDTO>> putBankAccount(@PathVariable UUID id, @Valid @RequestBody BankAccountPutDTO bankPutDTO) {
        BankAccountGetDTO bankAccountGetDTO = bankAccountService.putBankAccount(id,  bankPutDTO);
        return createResponse(
                HttpStatus.OK,
                bankAccountGetDTO.id(),
                bankAccountGetDTO,
                messageUtils.getMessage("bank.account.updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable UUID id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveBankAccount(@PathVariable UUID id) {
        bankAccountService.inactiveBankAccount(id);
        return ResponseEntity.noContent().build();
    }
}
