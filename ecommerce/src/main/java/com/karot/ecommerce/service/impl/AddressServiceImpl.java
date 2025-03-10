package com.karot.ecommerce.service.impl;

import com.karot.ecommerce.dto.AddressDto;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.entity.Address;
import com.karot.ecommerce.entity.User;
import com.karot.ecommerce.mapper.EntityDtoMapper;
import com.karot.ecommerce.repository.AddressRepo;
import com.karot.ecommerce.service.interf.AddressService;
import com.karot.ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserService userService;

    @Override
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();
        Address address = user.getAddress();

        if(address == null){
            address = new Address();
            address.setUser(user);
        }
        if (addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if (addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if (addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());
        if (addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());

        addressRepo.save(address);
        String message = (user.getAddress() == null) ? "Address successfully created" : "Address successfully updated";
        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }
}
