package br.com.c137.project.financial.services.services;

import br.com.c137.project.financial.services.exceptions.NotFoundException;
import br.com.c137.project.financial.services.mappers.CategoryMapper;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.auxiliaries.IdNameEntitiesAuxiliary;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.CategoryGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.CategoryPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.CategoryPutDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.services.multitenancy.tenant.models.Category;
import br.com.c137.project.financial.services.multitenancy.tenant.repositories.CategoryRepository;
import br.com.c137.project.financial.services.utils.MessageUtils;
import br.com.c137.project.financial.services.validations.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryValidation categoryValidation;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "categories", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<CategoryGetDTO> getAll(Pageable pageable) {
        Page<CategoryGetDTO> categories = categoryRepository.findAllBy(pageable, CategoryGetDTO.class);
        return new PagedModel<>(categories);
    }

    @Cacheable(value = "category", key = "#id")
    public CategoryGetDTO getCategoryById(UUID id) {
        return categoryRepository.findById(id, CategoryGetDTO.class).orElseThrow(
                () -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryGetDTO postCategory(CategoryPostDTO categoryPostDTO) {
        categoryValidation.nameExistsValidation(categoryPostDTO.name());
        Category category = categoryMapper.postToCategory(categoryPostDTO);
        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryGetDTO(category);
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", key = "#id")
    })
    public CategoryGetDTO putCategory(UUID id, CategoryPutDTO categoryPutDTO) {
        categoryValidation.nameExistsInOtherIdValidation(categoryPutDTO.name(), id);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        category = categoryMapper.putToCategory(categoryPutDTO, category);
        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryGetDTO(category);
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", key = "#id")
    })
    public void deleteCategory(UUID id) {
        categoryExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "category", key = "#id")
    })
    public void inactiveCategory(UUID id) {
        categoryExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void categoryExistsValidation(UUID id) {
        categoryValidation.categoryExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        categoryRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage(){
        return messageUtils.getMessage("category.not-found");
    }

    protected IdNameEntitiesAuxiliary getIdNameCategory(UUID id){
        return categoryRepository.findById(id, IdNameEntitiesAuxiliary.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }
}
