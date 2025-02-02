package com.ll.hackathon1team.global.security;

import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetailsImpl loadUserByUsername(String userPk) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userPk)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userPk));

        return new UserDetailsImpl(user);
    }

    public UserDetailsImpl loadUserByToken(String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        return loadUserByUsername(userPk);
    }
}
