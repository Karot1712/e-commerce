package com.karot.ecommerce.service.impl;

import com.karot.ecommerce.dto.CategoryDto;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.entity.Category;
import com.karot.ecommerce.exception.NotFoundException;
import com.karot.ecommerce.mapper.EntityDtoMapper;
import com.karot.ecommerce.repository.CategoryRepo;
import com.karot.ecommerce.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final EntityDtoMapper entityDtoMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        categoryRepo.delete(category);
        return Response.builder()
                .status(200)
                .message("Category deleted successfully")
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found "));
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("Category saved successfully")
                .build();
    }

    @Override
    public Response getAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(entityDtoMapper::mapCategoryDtoBasic)
                .collect(Collectors.toList());
        return Response.builder()
                .status(200)
                .categoryList(categoryDtos)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryDtoBasic(category);

        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }
}
