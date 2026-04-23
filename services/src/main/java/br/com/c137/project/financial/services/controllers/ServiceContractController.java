package br.com.c137.project.financial.services.controllers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.servicecontract.ServiceContractGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.servicecontract.ServiceContractPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.servicecontract.ServiceContractPutDTO;
import br.com.c137.project.financial.services.responses.ResponsePayload;
import br.com.c137.project.financial.services.services.ServiceContractService;
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
@RequestMapping("/servicecontract")
public class ServiceContractController {
    @Autowired
    private ServiceContractService serviceContractService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<ServiceContractGetDTO>> getAll(
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(serviceContractService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ServiceContractGetDTO>> getServiceContractById(@PathVariable UUID id) {
        ServiceContractGetDTO serviceContractGetDTO = serviceContractService.getServiceContractById(id);
        return createResponse(
                HttpStatus.OK,
                serviceContractGetDTO.id(),
                serviceContractGetDTO,
                messageUtils.getMessage("service.contract.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ServiceContractGetDTO>> postServiceContract(@Valid @RequestBody ServiceContractPostDTO serviceContractPostDTO) {
        ServiceContractGetDTO serviceContractGetDTO = serviceContractService.postServiceContract(serviceContractPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                serviceContractGetDTO.id(),
                serviceContractGetDTO,
                messageUtils.getMessage("service.contract.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ServiceContractGetDTO>> putServiceContract(@PathVariable UUID id, @Valid @RequestBody ServiceContractPutDTO serviceContractPutDTO) {
        ServiceContractGetDTO serviceContractGetDTO = serviceContractService.putServiceContract(id, serviceContractPutDTO);
        return createResponse(
                HttpStatus.OK,
                serviceContractGetDTO.id(),
                serviceContractGetDTO,
                messageUtils.getMessage("service.contract.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceContract(@PathVariable UUID id) {
        serviceContractService.deleteServiceContract(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveServiceContract(@PathVariable UUID id) {
        serviceContractService.inactiveServiceContract(id);
        return ResponseEntity.noContent().build();
    }
}
