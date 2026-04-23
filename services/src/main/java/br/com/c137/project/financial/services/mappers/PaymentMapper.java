package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.PaymentGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.PaymentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.PaymentPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentGetDTO paymentToPaymentGetDTO(Payment payment);
    Payment postToPayment(PaymentPostDTO paymentPostDTO);
    Payment putToPayment(PaymentPutDTO paymentPutDTO, @MappingTarget Payment payment);
}
