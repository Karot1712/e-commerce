package com.karot.ecommerce.controller;

import com.karot.ecommerce.dto.CategoryDto;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryId,categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("/get-by-id/{categoryId}")
    public ResponseEntity<Response> getCategoryById(@RequestParam Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

}
