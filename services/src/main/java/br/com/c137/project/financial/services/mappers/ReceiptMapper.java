package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.ReceiptGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.ReceiptPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.ReceiptPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceiptMapper {
    ReceiptGetDTO  receiptToReceiptGetDTO(Receipt receipt);
    Receipt postToReceipt(ReceiptPostDTO receiptPostDTO);
    Receipt putToReceipt(ReceiptPutDTO receiptPutDTO, @MappingTarget Receipt receipt);
}
