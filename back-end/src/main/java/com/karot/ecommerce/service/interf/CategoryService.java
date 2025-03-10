package com.karot.ecommerce.service.interf;

import com.karot.ecommerce.dto.CategoryDto;
import com.karot.ecommerce.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryRequest);

    Response deleteCategory(Long categoryId);

    Response updateCategory(Long categoryId, CategoryDto categoryRequest);

    Response getAllCategory();

    Response getCategoryById(Long categoryId);


}
