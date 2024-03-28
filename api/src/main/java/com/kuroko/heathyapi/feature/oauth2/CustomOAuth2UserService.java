package com.kuroko.heathyapi.feature.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.account.model.Role;
import com.kuroko.heathyapi.feature.oauth2.user.OAuth2UserInfo;
import com.kuroko.heathyapi.feature.oauth2.user.OAuth2UserInfoFactory;
import com.kuroko.heathyapi.feature.user.model.Gender;
import com.kuroko.heathyapi.feature.user.model.Goal;
import com.kuroko.heathyapi.feature.user.model.User;

@Component
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        // Extract user info
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2User.getName();
        String avatarUrl = oAuth2UserInfo.getImageUrl();
        // Add other attributes as needed

        // Save user info to database
        Account account = accountRepository.findByEmail(email).orElse(null);
        if (account == null) {
            account = new Account();
            account.setEmail(email);
            account.setProvider(AuthProvider.from(provider));
            account.setRole(Role.USER);
            User user = new User();
            user.setName(name);
            user.setAvatarURL(avatarUrl);
            user.setGender(Gender.MALE); // default
            user.setGoal(Goal.MAINTAIN); // default
            user.setCoefficientOfActivity(1.2); // default
            account.setUser(user);
            user.setAccount(account);
            // Set other attributes
            accountRepository.save(account);
        }

        return oAuth2User;
    }

}