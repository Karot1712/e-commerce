package com.karot.ecommerce.service.impl;

import com.karot.ecommerce.dto.ProductDto;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.entity.Category;
import com.karot.ecommerce.entity.Product;
import com.karot.ecommerce.exception.NotFoundException;
import com.karot.ecommerce.mapper.EntityDtoMapper;
import com.karot.ecommerce.repository.CategoryRepo;
import com.karot.ecommerce.repository.ProductRepo;
import com.karot.ecommerce.service.CloudinaryService;
import com.karot.ecommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final EntityDtoMapper entityDtoMapper;
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final CloudinaryService cloudinaryService;


    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        String productImageUrl = cloudinaryService.saveImageToCloud(image);

        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product created successfully ")
                .build();
    }

    @Override
    public Response updateProduct(Long categoryId, Long productId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found"));

        Category category = null;
        String productImageUrl =null;

        //check if user want to change them then get the value
        if(categoryId != null){
            category = categoryRepo.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found"));
        }
        if(image != null && !image.isEmpty()){
            productImageUrl = cloudinaryService.saveImageToCloud(image);
        }

        if(category != null) product.setCategory(category);
        if(name != null) product.setName(name);
        if(description != null) product.setDescription(description);
        if(price != null) product.setPrice(price);
        if(image != null) product.setImageUrl(productImageUrl);

        productRepo.save(product);

        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found"));
        productRepo.delete(product);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully ")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found"));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        //find and sort descending by id
        List<ProductDto> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC,"id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public Response getProductByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);

        if(products.isEmpty()){
            throw new NotFoundException("No product found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameContainingOrDescriptionContaining(searchValue,searchValue);

        if (products.isEmpty()){
            throw new NotFoundException("No products found");
        }

        List<ProductDto> productDtoList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

}
