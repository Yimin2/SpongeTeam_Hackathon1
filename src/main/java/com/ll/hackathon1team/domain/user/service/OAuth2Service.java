package com.ll.hackathon1team.domain.user.service;

import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.domain.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String userName = oAuth2User.getAttribute("name");
        String image = oAuth2User.getAttribute("picture");

        User user = userRepository.findByEmail(email)
                .map(existingUser -> updateUser(existingUser, userName, image))
                .orElseGet(() -> createUser(email, userName, image));

        userRepository.save(user);

        return oAuth2User;
    }

    private User updateUser(User user, String userName, String image) {
        user.setUserName(userName);
        user.setImage(image);
        return user;
    }

    private User createUser(String email, String userName, String image) {
        return User.builder()
                .email(email)
                .userName(userName)
                .image(image)
                .build();
    }
}
