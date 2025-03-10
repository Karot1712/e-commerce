package com.karot.ecommerce.controller;

import com.karot.ecommerce.dto.Response;
import com.karot.ecommerce.dto.UserDto;
import com.karot.ecommerce.entity.User;
import com.karot.ecommerce.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/my-info")
    public ResponseEntity<Response> getUserInfoAndOrderHistory(){
        return ResponseEntity.ok(userService.getUserInfoAndOrderHistory());

    }
}
