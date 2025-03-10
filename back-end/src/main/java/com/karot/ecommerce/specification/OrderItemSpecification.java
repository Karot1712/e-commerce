package com.karot.ecommerce.specification;

import com.karot.ecommerce.entity.OrderItem;
import com.karot.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderItemSpecification {

    //Specification to filter order items status
    public static Specification<OrderItem> hasStatus(OrderStatus status){
        return ((root, query, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status): null);
    }

    //Specification to filter order items by data range
    public static Specification<OrderItem> createdBetween(LocalDateTime startDate, LocalDateTime endDate){
        return ((root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null){
                return criteriaBuilder.between(root.get("createdAt "),startDate,endDate);
            } else if (startDate != null ) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), endDate);
            }else {
                return null;
            }
        });
    }

    //Generate specification to filter order items by item id
    public static  Specification<OrderItem> hasItemId(Long itemId){
        return ((root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("id"),itemId) : null);
    }
}
