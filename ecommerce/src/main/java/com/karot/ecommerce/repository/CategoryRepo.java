package com.karot.ecommerce.repository;

import com.karot.ecommerce.entity.Category;
import com.karot.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepo extends JpaRepository<Category, Long>, JpaSpecificationExecutor<OrderItem> {
}
