package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.BankAccountGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.BankAccountPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.BankAccountPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountGetDTO bankAccountToBankAccountGetDTO(BankAccount bankAccount);
    BankAccount postToBankAccount(BankAccountPostDTO bankAccountPostDTO);
    BankAccount putToBankAccount(BankAccountPutDTO bankAccountPutDTO, @MappingTarget BankAccount bankAccount);
}
