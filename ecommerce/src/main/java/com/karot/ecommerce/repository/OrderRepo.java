package com.karot.ecommerce.repository;

import com.karot.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long>  {
}
