package br.com.c137.project.financial.services.multitenancy.tenant.dtos.posts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryPostDTO(
        @NotBlank(message = "Category name is required")
        @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
        String name,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String description
) {

}
