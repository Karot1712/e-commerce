package com.karot.ecommerce.service.interf;

import com.karot.ecommerce.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {
    Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price);

    Response updateProduct( Long categoryId ,Long productId, MultipartFile image, String name, String description, BigDecimal price);

    Response deleteProduct(Long productId);

    Response getProductById(Long productId);

    Response getAllProducts();

    Response searchProduct(String searchValue);

    Response getProductByCategory(Long categoryId);
}
