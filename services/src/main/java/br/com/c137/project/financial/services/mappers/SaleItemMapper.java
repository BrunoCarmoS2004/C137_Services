package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.sale.SaleItemGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.sale.SaleItemPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.sales.SaleItemPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.sale.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {
    SaleItemGetDTO saleItemToSaleItemGetDTO(SaleItem saleItem);
    SaleItem postToSaleItem(SaleItemPostDTO saleItem);
    SaleItem putToSaleItem(SaleItemPutDTO saleItemPutDTO, @MappingTarget SaleItem saleItem);
}
