package br.com.c137.project.financial.services.controllers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.PaymentGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.PaymentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.PaymentPutDTO;
import br.com.c137.project.financial.services.responses.ResponsePayload;
import br.com.c137.project.financial.services.services.PaymentService;
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
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<PaymentGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
                                                         Pageable pageable) {
        return ResponseEntity.ok(paymentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<PaymentGetDTO>> getPaymentById(@PathVariable UUID id) {
        PaymentGetDTO paymentGetDTO = paymentService.getPaymentById(id);
        return createResponse(
                HttpStatus.OK,
                paymentGetDTO.id(),
                paymentGetDTO,
                messageUtils.getMessage("payment.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<PaymentGetDTO>> postPayment(@Valid @RequestBody PaymentPostDTO paymentPostDTO) {
        PaymentGetDTO paymentGetDTO = paymentService.postPayment(paymentPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                paymentGetDTO.id(),
                paymentGetDTO,
                messageUtils.getMessage("payment.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<PaymentGetDTO>> putPayment(@PathVariable UUID id, @Valid @RequestBody PaymentPutDTO paymentPutDTO) {
        PaymentGetDTO paymentGetDTO = paymentService.putPayment(id, paymentPutDTO);
        return createResponse(
                HttpStatus.OK,
                paymentGetDTO.id(),
                paymentGetDTO,
                messageUtils.getMessage("payment.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactivePayment(@PathVariable UUID id) {
        paymentService.inactivePayment(id);
        return ResponseEntity.noContent().build();
    }
}
