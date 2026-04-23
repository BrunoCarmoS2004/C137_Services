package br.com.c137.project.financial.services.controllers;

import br.com.c137.project.financial.services.multitenancy.tenant.dtos.gets.CategoryGetDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts.CategoryPostDTO;
import br.com.c137.project.financial.services.multitenancy.tenant.dtos.puts.CategoryPutDTO;
import br.com.c137.project.financial.services.responses.ResponsePayload;
import br.com.c137.project.financial.services.services.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<CategoryGetDTO>> getAll(
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<CategoryGetDTO>> getCategoryById(@PathVariable UUID id) {
        CategoryGetDTO categoryGetDTO = categoryService.getCategoryById(id);
        return createResponse(
                HttpStatus.OK,
                categoryGetDTO.id(),
                categoryGetDTO,
                messageUtils.getMessage("category.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<CategoryGetDTO>> postCategory(@Valid @RequestBody CategoryPostDTO categoryPostDTO) {
        CategoryGetDTO categoryGetDTO = categoryService.postCategory(categoryPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                categoryGetDTO.id(),
                categoryGetDTO,
                messageUtils.getMessage("category.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<CategoryGetDTO>> putCategory(@PathVariable UUID id, @Valid @RequestBody CategoryPutDTO categoryPutDTO) {
        CategoryGetDTO categoryGetDTO = categoryService.putCategory(id, categoryPutDTO);
        return createResponse(
                HttpStatus.OK,
                categoryGetDTO.id(),
                categoryGetDTO,
                messageUtils.getMessage("category.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveCategory(@PathVariable UUID id) {
        categoryService.inactiveCategory(id);
        return ResponseEntity.noContent().build();
    }
}
