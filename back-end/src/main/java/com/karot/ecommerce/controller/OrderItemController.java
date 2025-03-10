package com.karot.ecommerce.controller;

import com.karot.ecommerce.dto.OrderRequest;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.enums.OrderStatus;
import com.karot.ecommerce.service.interf.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-item")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/place-order")
    public ResponseEntity<Response> placeOrder(@RequestParam OrderRequest orderRequest){
        return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
    }

    @PutMapping("/update/{orderItemId}")
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable Long orderItemId, @RequestParam String status){
        return ResponseEntity.ok(orderItemService.updateOrderItemStatus(orderItemId,status));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> filterOrderItem(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));
        OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : null;
        return ResponseEntity.ok(orderItemService.filterOrderItem(orderStatus,startDate,endDate,itemId, pageable));
    }

}
