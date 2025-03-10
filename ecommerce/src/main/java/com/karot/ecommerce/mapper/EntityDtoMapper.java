package com.karot.ecommerce.mapper;

import com.karot.ecommerce.dto.*;
import com.karot.ecommerce.entity.*;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {

    //User -> DTO
    public UserDto mapUserDtoBasic(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        return userDto;
    }

    //Address -> DTO
    public AddressDto mapAddressDtoBasic(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setZipCode(address.getZipCode());
        addressDto.setCountry(address.getCountry());
        return addressDto;
    }

    //Category -> DTO
    public CategoryDto mapCategoryDtoBasic(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    //OrderItem -> DTO
    public OrderItemDto mapOrderItemDtoBasic(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setStatus(orderItem.getStatus().name());
        orderItemDto.setCreatedAt(orderItem.getCreatedAt());
        return orderItemDto;
    }

    //Product -> DTO
    public ProductDto mapProductToDtoBasic(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }

    public UserDto mapUserToDtoWithAddress(User user){
        UserDto userDto = mapUserDtoBasic(user);
        if(user.getAddress() != null){
            AddressDto addressDto = mapAddressDtoBasic(user.getAddress());
            userDto.setAddress(addressDto);
        }
        return userDto;
    }

    //orderItem -> DTO + product
    public OrderItemDto mapOrderItemToDtoWithProduct(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemDtoBasic(orderItem);

        if(orderItem.getProduct() != null){
            ProductDto productDto = mapProductToDtoBasic(orderItem.getProduct());
            orderItemDto.setProduct(productDto);
        }
        return orderItemDto;
    }

    //orderItem -> DTO + product + user
    public OrderItemDto mapOrderItemToDtoWithProductAndUser(OrderItem orderItem){
        OrderItemDto orderItemDto = mapOrderItemToDtoWithProduct(orderItem);
        if(orderItem.getUser() != null){
            UserDto userDto =  mapUserToDtoWithAddress(orderItem.getUser());
            orderItemDto.setUser(userDto);
        }
        return orderItemDto;
    }

    //User -> DTO + Address + OrderItem History
    public UserDto mapUserToDtoWithAddressAndOrderHistory(User user){
        UserDto userDto =  mapUserToDtoWithAddress(user);

        if(user.getOrderItemList() != null && !user.getOrderItemList().isEmpty()){
            userDto.setOrderItemList(user.getOrderItemList()
                    .stream()
                    .map(this::mapOrderItemToDtoWithProduct)
                    .collect(Collectors.toList()));
        }
        return userDto;
    }

}
