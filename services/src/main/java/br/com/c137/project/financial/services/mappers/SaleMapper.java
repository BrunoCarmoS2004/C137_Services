package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale.SaleGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.InstallmentPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SaleItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SalePostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales.SalePutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.Installment;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.Sale;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.SaleItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleGetDTO saleToSaleGetDTO(Sale salePostDTO);
    Sale postToSale(SalePostDTO salePostDTO);
    Sale putToSale(SalePutDTO salePutDTO, @MappingTarget Sale sale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "entityStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    SaleItem itemPostToSaleItem(SaleItemPostDTO saleItemPostDTO);

    @AfterMapping
    default void linkItems(@MappingTarget Sale sale) {
        if (sale.getSaleItems() != null) {
            sale.getSaleItems().forEach(item -> item.setSale(sale));
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "entityStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Installment installmentPostToInstallment(InstallmentPostDTO installmentPostDTO);

    @AfterMapping
    default void linkInstallments(@MappingTarget Sale sale) {
        if (sale.getInstallments() != null) {
            sale.getInstallments().forEach(item -> item.setSale(sale));
        }
    }
}
