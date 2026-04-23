package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.mappers.ServiceContractMapper;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.ServiceContractAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.IdNameBankAccountGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract.ServiceContractGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract.ServiceContractItemPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract.ServiceContractPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.basic.Client;
import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContract;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.servicecontract.ServiceContractRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.ServiceContractValidation;
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
public class ServiceContractService {

    @Autowired
    private ServiceContractRepository serviceContractRepository;

    @Autowired
    private ServiceContractMapper serviceContractMapper;

    @Autowired
    private ServiceContractValidation serviceContractValidation;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProductService serviceProductService;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "servicecontracts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<ServiceContractGetDTO> getAll(Pageable pageable) {
        Page<ServiceContract> page = serviceContractRepository.findAll(pageable);
        Page<ServiceContractGetDTO> serviceContracts = page.map(serviceContractMapper::serviceContractToServiceContractGetDTO);
        return new PagedModel<>(serviceContracts);
    }

    @Cacheable(value = "servicecontract", key = "#id")
    public ServiceContractGetDTO getServiceContractById(UUID id) {
        ServiceContract serviceContract = serviceContractRepository.findById(id).orElseThrow(
                () -> new NotFoundException(getNotFoundMessage()));
        return serviceContractMapper.serviceContractToServiceContractGetDTO(serviceContract);
    }

    @CacheEvict(value = "servicecontracts", allEntries = true)
    public ServiceContractGetDTO postServiceContract(ServiceContractPostDTO serviceContractPostDTO) {
        Set<UUID> allItemsIds = serviceContractPostDTO.serviceContractItems().stream().map(ServiceContractItemPostDTO::serviceProductId).collect(Collectors.toSet());
        ServiceContractAuxiliary validations = commonValidations(serviceContractPostDTO.clientId(), serviceContractPostDTO.bankAccountId(), allItemsIds);
        ServiceContract serviceContract = serviceContractMapper.postToServiceContract(serviceContractPostDTO);
        return saveAndReturn(serviceContract, validations);
    }

    @Caching(evict = {
            @CacheEvict(value = "servicecontracts", allEntries = true),
            @CacheEvict(value = "servicecontract", key = "#id")
    })
    public ServiceContractGetDTO putServiceContract(UUID id, ServiceContractPutDTO serviceContractPutDTO) {
        serviceContractExistsValidation(id);
        Set<UUID> allItemsIds = serviceContractPutDTO.serviceContractItems().stream().map(ServiceContractItemPutDTO::serviceProductId).collect(Collectors.toSet());
        ServiceContractAuxiliary validations = commonValidations(serviceContractPutDTO.clientId(), serviceContractPutDTO.bankAccountId(), allItemsIds);
        ServiceContract serviceContract = serviceContractRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        serviceContract = serviceContractMapper.putToServiceContract(serviceContractPutDTO, serviceContract);
        return saveAndReturn(serviceContract, validations);
    }

    @Caching(evict = {
            @CacheEvict(value = "servicecontracts", allEntries = true),
            @CacheEvict(value = "servicecontract", key = "#id")
    })
    public void deleteServiceContract(UUID id) {
        serviceContractExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "servicecontracts", allEntries = true),
            @CacheEvict(value = "servicecontract", key = "#id")
    })
    public void inactiveServiceContract(UUID id) {
        serviceContractExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void serviceContractExistsValidation(UUID id) {
        serviceContractValidation.serviceContractExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        serviceContractRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage() {
        return messageUtils.getMessage("service.contract.not-found");
    }

    private ServiceContractAuxiliary commonValidations(UUID clientId, UUID bankAccountId, Set<UUID> itemIds) {
        clientService.clientExistsValidation(clientId);
        bankAccountService.bankAccountExistsValidation(bankAccountId);
        serviceProductService.serviceProductExistsByIdsValidation(itemIds);

        Client client = clientService.getClientById(clientId);
        IdNameBankAccountGetDTO bankAccount = bankAccountService.getIdNameBankAccount(bankAccountId);
        return new ServiceContractAuxiliary(client.getName(), bankAccount.name());
    }

    private ServiceContractGetDTO saveAndReturn(ServiceContract serviceContract, ServiceContractAuxiliary auxiliary) {
        serviceContract.setClientName(auxiliary.clientName());
        serviceContract.setBankAccountName(auxiliary.bankAccountName());
        serviceContract = serviceContractRepository.save(serviceContract);
        return serviceContractMapper.serviceContractToServiceContractGetDTO(serviceContract);
    }
}
