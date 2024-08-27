package com.ll.hackathon1team.domain.user.service;

import com.ll.hackathon1team.domain.reivew.repository.ReviewRepository;
import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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


    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        reviewRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
