package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale.InstallmentGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.InstallmentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales.InstallmentPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.Installment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstallmentMapper {
    InstallmentGetDTO installmentToInstallmentGetDTO(Installment installment);
    Installment postToInstallment(InstallmentPostDTO installmentPostDTO);
    Installment putToInstallment(InstallmentPutDTO installmentPutDTO, @MappingTarget Installment installment);
}
