package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract.ServiceContractGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract.ServiceContractPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContract;
import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContractItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ServiceContractMapper {
    ServiceContractGetDTO serviceContractToServiceContractGetDTO(ServiceContract serviceContract);
    ServiceContract postToServiceContract(ServiceContractPostDTO serviceContractPostDTO);
    ServiceContract putToServiceContract(ServiceContractPutDTO serviceContractPutDTO, @MappingTarget ServiceContract serviceContract);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "serviceContract", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "entityStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    ServiceContractItem itemPostDtoToServiceContractItem(ServiceContractItemPostDTO serviceContractItemPostDTO);

    @AfterMapping
    default void linkItems(@MappingTarget ServiceContract serviceContract) {
        if (serviceContract.getServiceContractItems() != null) {
            serviceContract.getServiceContractItems().forEach(item -> item.setServiceContract(serviceContract));
        }
    }
}
