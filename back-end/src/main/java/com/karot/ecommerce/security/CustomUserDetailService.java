package com.karot.ecommerce.security;

import com.karot.ecommerce.entity.User;
import com.karot.ecommerce.exception.NotFoundException;
import com.karot.ecommerce.repository.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy User/Email"));

        return AuthUser.builder()
                .user(user)
                .build();
    }
}
