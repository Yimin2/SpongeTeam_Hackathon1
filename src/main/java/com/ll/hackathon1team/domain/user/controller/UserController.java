package com.ll.hackathon1team.domain.user.controller;

import com.ll.hackathon1team.domain.user.service.OAuth2Service;
import com.ll.hackathon1team.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API", description = "User API")
public class UserController {

    private final OAuth2Service oAuth2Service;

    @Operation(summary = "회원탈퇴", description = "인증정보를 확인하여, user 계정, review 삭제")
    @DeleteMapping("api/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();

        oAuth2Service.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
