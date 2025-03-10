package com.karot.ecommerce.service.impl;

import com.karot.ecommerce.dto.LoginRequest;
import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.dto.UserDto;
import com.karot.ecommerce.entity.User;
import com.karot.ecommerce.enums.UserRole;
import com.karot.ecommerce.exception.InvalidCredentialException;
import com.karot.ecommerce.exception.NotFoundException;
import com.karot.ecommerce.mapper.EntityDtoMapper;
import com.karot.ecommerce.repository.UserRepo;
import com.karot.ecommerce.security.JwtUtils;
import com.karot.ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Response registerUser(UserDto registrationRequest) {
        UserRole role = UserRole.USER;

        if(registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")){
            role = UserRole.ADMIN;
        }
        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser = userRepo.save(user);
        UserDto userDto = entityDtoMapper.mapUserDtoBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User Successfully Added")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFoundException("Email not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialException("Password does not match");
        }
        String token  = jwtUtils.generateToken(user);
        return Response.builder()
                .status(200)
                .message("Log in success")
                .token(token)
                .expirationTime("1 month")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUser() {
        List<UserDto> userList = userRepo.findAll()
                .stream()
                .map(entityDtoMapper::mapUserDtoBasic)
                .toList();

        return Response.builder()
                .status(200)
                .userList(userList)
                .build();
    }

    @Override
    public User getLoginUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User email is: " + email);

        return userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoWithAddressAndOrderHistory(user);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }
}
