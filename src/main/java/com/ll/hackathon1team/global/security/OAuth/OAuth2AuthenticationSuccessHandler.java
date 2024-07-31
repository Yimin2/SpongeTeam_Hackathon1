package com.ll.hackathon1team.global.security.OAuth;

import com.ll.hackathon1team.global.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(oAuth2User.getAttribute("email"));

        response.setHeader("Authorization", "Bearer " + token);

        log.info("OAuth2 인증 성공: 사용자 이메일 {}, JWT 토큰: {}", oAuth2User.getAttribute("email"), token);
        response.sendRedirect("/");
    }
}
