package com.karot.ecommerce.service.interf;

import com.karot.ecommerce.dto.AddressDto;
import com.karot.ecommerce.dto.Response;
import org.springframework.stereotype.Service;


public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);

}
