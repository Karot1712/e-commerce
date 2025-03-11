package com.karot.ecommerce.service.impl;

import com.karot.ecommerce.dto.OrderItemDto;
import com.karot.ecommerce.dto.OrderItemRequest;
import com.karot.ecommerce.dto.OrderRequest;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.entity.Order;
import com.karot.ecommerce.entity.OrderItem;
import com.karot.ecommerce.entity.Product;
import com.karot.ecommerce.entity.User;
import com.karot.ecommerce.enums.OrderStatus;
import com.karot.ecommerce.exception.NotFoundException;
import com.karot.ecommerce.mapper.EntityDtoMapper;
import com.karot.ecommerce.repository.OrderItemRepo;
import com.karot.ecommerce.repository.OrderRepo;
import com.karot.ecommerce.repository.ProductRepo;
import com.karot.ecommerce.service.interf.OrderItemService;
import com.karot.ecommerce.service.interf.UserService;
import com.karot.ecommerce.specification.OrderItemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepo orderItemRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserService userService;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        User user = userService.getLoginUser();

        //map order request items to order entities
        List<OrderItem> orderItems = orderRequest.getItemRequest().stream().map(orderItemRequest -> {
            Product product = productRepo.findById(orderItemRequest.getProductId())
                    .orElseThrow(()->new NotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))); // set price according to quantity
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return  orderItem;
        }).collect(Collectors.toList());


        //calculate total price
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        //create order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        //set order reference in each order item
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderRepo.save(order);

        return Response.builder()
                .status(200)
                .message("Order was successfully placed")
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new NotFoundException("Order item not found"));
        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);
        return Response.builder()
                .status(200)
                .message("Order status updated successfully")
                .build();
    }

    @Override
    public Response filterOrderItem(OrderStatus orderStatus, LocalDateTime startDate, LocalDateTime endDate, Long itemId, org.springframework.data.domain.Pageable pageable) {
        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(orderStatus))
                .and(OrderItemSpecification.createdBetween(startDate,endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec,pageable);

        if(orderItemPage.isEmpty()){
            throw new NotFoundException("No order found");
        }
        List<OrderItemDto> orderItemDtos = orderItemPage.getContent()
                .stream()
                .map(entityDtoMapper::mapOrderItemToDtoWithProductAndUser)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .orderItemList(orderItemDtos)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }
}
