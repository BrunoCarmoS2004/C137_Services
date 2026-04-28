package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.mappers.SaleMapper;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.sale.SaleAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale.SaleGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SaleItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SalePostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales.SalePutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.Sale;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.sale.SaleRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.SaleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleValidation saleValidation;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProductService serviceProductService;

    @Autowired
    private MessageUtils messageUtils;


    @Cacheable(value = "sales", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<SaleGetDTO> getAll(Pageable pageable) {
        Page<Sale> page = saleRepository.findAll(pageable);
        Page<SaleGetDTO> sales = page.map(saleMapper::saleToSaleGetDTO);
        return new PagedModel<>(sales);
    }

    @Cacheable(value = "sale", key = "#id")
    public SaleGetDTO getSaleById(UUID id) {
        Sale sale = saleRepository.findById(id).orElseThrow(
                () -> new NotFoundException(getNotFoundMessage()));
        return saleMapper.saleToSaleGetDTO(sale);
    }

    @CacheEvict(value = "sales", allEntries = true)
    public SaleGetDTO postSale(SalePostDTO salePostDTO) {
        Set<UUID> allItemsIds = salePostDTO.saleItems().stream().map(SaleItemPostDTO::serviceProductId).collect(Collectors.toSet());
        SaleAuxiliary validations = resolveSaleDependencies(salePostDTO.clientId(), salePostDTO.bankAccountId(), allItemsIds);
        Sale sale = saleMapper.postToSale(salePostDTO);
        return saveAndReturn(sale, validations);
    }

    @Caching(evict = {
            @CacheEvict(value = "sales", allEntries = true),
            @CacheEvict(value = "sale", key = "#id")
    })
    public SaleGetDTO putSale(UUID id, SalePutDTO salePutDTO) {
        saleExistsValidation(id);
        Set<UUID> allItemsIds = salePutDTO.saleItems().stream().map(SaleItemPostDTO::serviceProductId).collect(Collectors.toSet());
        SaleAuxiliary validations = resolveSaleDependencies(salePutDTO.clientId(), salePutDTO.bankAccountId(), allItemsIds);
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        sale = saleMapper.putToSale(salePutDTO, sale);
        return saveAndReturn(sale, validations);
    }

    @Caching(evict = {
            @CacheEvict(value = "sales", allEntries = true),
            @CacheEvict(value = "sale", key = "#id")
    })
    public void deleteSale(UUID id) {
        saleExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "sales", allEntries = true),
            @CacheEvict(value = "sale", key = "#id")
    })
    public void inactiveSale(UUID id) {
        saleExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void saleExistsValidation(UUID id) {
        saleValidation.saleExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        saleRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage() {
        return messageUtils.getMessage("sale.not-found");
    }

    private SaleAuxiliary resolveSaleDependencies(UUID clientId, UUID bankAccountId, Set<UUID> itemIds) {
        serviceProductService.serviceProductExistsByIdsValidation(itemIds);
        //Validations if exists in services
        IdNameEntitiesAuxiliary idNameClient = clientService.getIdNameClient(clientId);
        IdNameEntitiesAuxiliary idNameBankAccount = bankAccountService.getIdNameBankAccount(bankAccountId);
        return new SaleAuxiliary(idNameClient, idNameBankAccount);
    }

    private SaleGetDTO saveAndReturn(Sale sale, SaleAuxiliary saleAuxiliary) {
        sale.setClientName(saleAuxiliary.clientInfo().name());
        sale.setBankAccountName(saleAuxiliary.bankAccountInfo().name());
        sale = saleRepository.save(sale);
        return saleMapper.saleToSaleGetDTO(sale);
    }
}
