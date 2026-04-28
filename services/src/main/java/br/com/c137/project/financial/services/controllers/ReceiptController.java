package br.com.c137.project.financial.services.controllers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.ReceiptGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.ReceiptPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.ReceiptPutDTO;
import br.com.c137.project.financial.services.responses.ResponsePayload;
import br.com.c137.project.financial.services.services.ReceiptService;
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
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<ReceiptGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
                                                         Pageable pageable) {
        return ResponseEntity.ok(receiptService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ReceiptGetDTO>> getReceiptById(@PathVariable UUID id) {
        ReceiptGetDTO receiptGetDTO = receiptService.getReceiptById(id);
        return createResponse(
                HttpStatus.OK,
                receiptGetDTO.id(),
                receiptGetDTO,
                messageUtils.getMessage("receipt.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ReceiptGetDTO>> postReceipt(@Valid @RequestBody ReceiptPostDTO receiptPostDTO) {
        ReceiptGetDTO receiptGetDTO = receiptService.postReceipt(receiptPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                receiptGetDTO.id(),
                receiptGetDTO,
                messageUtils.getMessage("receipt.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ReceiptGetDTO>> putReceipt(@PathVariable UUID id, @Valid @RequestBody ReceiptPutDTO receiptPutDTO) {
        ReceiptGetDTO receiptGetDTO = receiptService.putReceipt(id, receiptPutDTO);
        return createResponse(
                HttpStatus.OK,
                receiptGetDTO.id(),
                receiptGetDTO,
                messageUtils.getMessage("receipt.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable UUID id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveReceipt(@PathVariable UUID id) {
        receiptService.inactiveReceipt(id);
        return ResponseEntity.noContent().build();
    }
}
