package br.com.c137.project.financial.services.controllers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale.SaleGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SalePostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales.SalePutDTO;
import br.com.c137.project.financial.services.responses.ResponsePayload;
import br.com.c137.project.financial.services.services.SaleService;
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
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<SaleGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
                                                         Pageable pageable) {
        return ResponseEntity.ok(saleService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<SaleGetDTO>> getSaleById(@PathVariable UUID id) {
        SaleGetDTO saleGetDTO = saleService.getSaleById(id);
        return createResponse(
                HttpStatus.OK,
                saleGetDTO.id(),
                saleGetDTO,
                messageUtils.getMessage("sale.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<SaleGetDTO>> postSale(@Valid @RequestBody SalePostDTO salePostDTO) {
        SaleGetDTO saleGetDTO = saleService.postSale(salePostDTO);
        return createResponse(
                HttpStatus.CREATED,
                saleGetDTO.id(),
                saleGetDTO,
                messageUtils.getMessage("sale.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<SaleGetDTO>> putSale(@PathVariable UUID id, @Valid @RequestBody SalePutDTO salePutDTO) {
        SaleGetDTO saleGetDTO = saleService.putSale(id, salePutDTO);
        return createResponse(
                HttpStatus.OK,
                saleGetDTO.id(),
                saleGetDTO,
                messageUtils.getMessage("sale.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable UUID id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveSale(@PathVariable UUID id) {
        saleService.inactiveSale(id);
        return ResponseEntity.noContent().build();
    }
}
