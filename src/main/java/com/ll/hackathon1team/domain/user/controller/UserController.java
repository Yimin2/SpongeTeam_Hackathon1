package com.ll.hackathon1team.domain.user.controller;

import com.ll.hackathon1team.global.security.JwtTokenProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;

    public UserController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/token")
    public String token(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String token = jwtTokenProvider.createToken(oAuth2User.getName());
        return "Bearer " + token;
    }
}
