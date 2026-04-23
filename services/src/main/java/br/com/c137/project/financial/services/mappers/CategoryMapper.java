package br.com.c137.project.financial.services.mappers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.CategoryGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.CategoryPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.CategoryPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryGetDTO categoryToCategoryGetDTO(Category category);
    Category postToCategory(CategoryPostDTO categoryPostDTO);
    Category putToCategory(CategoryPutDTO categoryPutDTO, @MappingTarget Category category);
}
