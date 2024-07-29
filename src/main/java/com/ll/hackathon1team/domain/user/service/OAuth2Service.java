package com.ll.hackathon1team.domain.user.service;

import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.domain.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String userName = oAuth2User.getAttribute("userName");
        String image = oAuth2User.getAttribute("image");

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setUserName(userName);
            existingUser.setImage(image);
            userRepository.save(existingUser);
        } else {
            User newUser = User.builder()
                    .email(email)
                    .userName(userName)
                    .image(image)
                    .build();
            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}
