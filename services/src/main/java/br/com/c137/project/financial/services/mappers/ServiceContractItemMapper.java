package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract.ServiceContractItemGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract.ServiceContractItemPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract.ServiceContractPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContract;
import br.com.c137.project.financial.services.multitenancy.tenant.models.servicecontract.ServiceContractItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceContractItemMapper {
    ServiceContractItemGetDTO  serviceContractItemToServiceContractItemGetDTO(ServiceContractItem serviceContractItem);
    ServiceContractItem postToServiceContract(ServiceContractItemPostDTO serviceContractItemPostDTO);
    ServiceContractItem putToServiceContract(ServiceContractItemPutDTO serviceContractItemPutDTO, @MappingTarget ServiceContractItem serviceContractItem);
}
