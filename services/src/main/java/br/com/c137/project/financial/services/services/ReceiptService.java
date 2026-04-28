package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.mappers.PaymentMapper;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.payment.PaymentAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.PaymentGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.PaymentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.PaymentPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Payment;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.PaymentRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.PaymentValidation;
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
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentValidation paymentValidation;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @Cacheable(value = "payments", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<PaymentGetDTO> getAll(Pageable pageable) {
        Page<PaymentGetDTO> payments = paymentRepository.findAllBy(pageable, PaymentGetDTO.class);
        return new PagedModel<>(payments);
    }

    @Cacheable(value = "payment", key = "#id")
    public PaymentGetDTO getPaymentById(UUID id) {
        return paymentRepository.findById(id, PaymentGetDTO.class).orElseThrow(
                () -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "payments", allEntries = true)
    public PaymentGetDTO postPayment(PaymentPostDTO paymentPostDTO) {
        PaymentAuxiliary paymentAuxiliary = resolvePaymentDependencies(
                paymentPostDTO.categoryId(),
                paymentPostDTO.bankAccountId(),
                paymentPostDTO.supplierId()
        );
        Payment payment = paymentMapper.postToPayment(paymentPostDTO);
        return saveAndReturn(payment, paymentAuxiliary);
    }

    @Caching(evict = {
            @CacheEvict(value = "payments", allEntries = true),
            @CacheEvict(value = "payment", key = "#id")
    })
    public PaymentGetDTO putPayment(UUID id, PaymentPutDTO paymentPutDTO) {
        PaymentAuxiliary paymentAuxiliary = resolvePaymentDependencies(
                paymentPutDTO.categoryId(),
                paymentPutDTO.bankAccountId(),
                paymentPutDTO.supplierId()
        );
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        payment = paymentMapper.putToPayment(paymentPutDTO, payment);
        return saveAndReturn(payment, paymentAuxiliary);
    }

    @Caching(evict = {
            @CacheEvict(value = "payments", allEntries = true),
            @CacheEvict(value = "payment", key = "#id")
    })
    public void deletePayment(UUID id) {
        paymentExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "payments", allEntries = true),
            @CacheEvict(value = "payment", key = "#id")
    })
    public void inactivePayment(UUID id) {
        paymentExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void paymentExistsValidation(UUID id) {
        paymentValidation.paymentExistsValidation(id);
    }


    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        paymentRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage() {
        return messageUtils.getMessage("payment.not-found");
    }

    private PaymentAuxiliary resolvePaymentDependencies(
            UUID categoryId,
            UUID bankAccountId,
            UUID supplierId
    ) {
        //Validations if exists in services
        IdNameEntitiesAuxiliary idNameCategory = categoryService.getIdNameCategory(categoryId);
        IdNameEntitiesAuxiliary idNameBankAccount = bankAccountService.getIdNameBankAccount(bankAccountId);
        IdNameEntitiesAuxiliary idNameSupplier =
                supplierId != null
                ? supplierService.getIdNameSupplier(supplierId) : new IdNameEntitiesAuxiliary(null, null);
        //SUPPLIER, BANK ACCOUNT, CATEGORY
        return new PaymentAuxiliary(idNameSupplier, idNameBankAccount, idNameCategory);
    }

    private PaymentGetDTO saveAndReturn(Payment payment, PaymentAuxiliary paymentAuxiliary) {
        payment.setSupplierName(paymentAuxiliary.supplierInfo().name());
        payment.setBankAccountName(paymentAuxiliary.bankAccountInfo().name());
        payment.setCategoryName(paymentAuxiliary.categoryInfo().name());
        payment = paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentGetDTO(payment);
    }
}
