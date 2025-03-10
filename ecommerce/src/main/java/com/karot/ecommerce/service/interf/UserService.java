package com.karot.ecommerce.service.interf;

import com.karot.ecommerce.dto.LoginRequest;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.dto.UserDto;
import com.karot.ecommerce.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUser();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
