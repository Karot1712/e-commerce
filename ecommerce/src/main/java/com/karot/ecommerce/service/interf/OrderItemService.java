package com.karot.ecommerce.service.interf;

import com.karot.ecommerce.dto.OrderRequest;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.enums.OrderStatus;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItem(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);



}
