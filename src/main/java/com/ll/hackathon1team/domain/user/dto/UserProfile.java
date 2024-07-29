package com.ll.hackathon1team.domain.user.dto;

import com.ll.hackathon1team.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
    private String username;
    private String email;
    private String image;

    public User toEntity() {
        return User.builder()
                .userName(this.username)
                .email(this.email)
                .image(this.image)
                .build();
    }
}
