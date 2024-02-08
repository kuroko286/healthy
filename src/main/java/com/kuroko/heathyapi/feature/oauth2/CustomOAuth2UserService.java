package com.kuroko.heathyapi.feature.oauth2;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kuroko.heathyapi.components.CustomUserDetails;
import com.kuroko.heathyapi.exception.security.AuthenticationException;
import com.kuroko.heathyapi.exception.security.OAuth2AuthenticationProcessingException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.account.model.Role;
import com.kuroko.heathyapi.feature.oauth2.user.OAuth2UserInfo;
import com.kuroko.heathyapi.feature.oauth2.user.OAuth2UserInfoFactory;
import com.kuroko.heathyapi.feature.user.UserRepository;
import com.kuroko.heathyapi.feature.user.model.User;

import lombok.RequiredArgsConstructor;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Account> accountOptional = accountRepository.findByEmail(oAuth2UserInfo.getEmail());
        Account account;
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            if (!account.getProvider()
                    .equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        account.getProvider() + " account. Please use your " + account.getProvider() +
                        " account to login.");
            }
            account = updateExistingUser(account, oAuth2UserInfo);
        } else {
            account = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return new CustomUserDetails(account, oAuth2User.getAttributes());
    }

    private Account registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Account account = new Account();
        User user = new User();

        account.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        account.setProviderId(oAuth2UserInfo.getId());
        account.setRole(Role.USER);
        user.setName(oAuth2UserInfo.getName());
        account.setEmail(oAuth2UserInfo.getEmail());
        user.setAvatarURL(oAuth2UserInfo.getImageUrl());
        // userRepository.save(user);
        user.setAccount(account);
        return accountRepository.save(account);
    }

    private Account updateExistingUser(Account existingAccount, OAuth2UserInfo oAuth2UserInfo) {
        User existingUser = existingAccount.getUser();
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setAvatarURL(oAuth2UserInfo.getImageUrl());
        userRepository.save(existingUser);
        return accountRepository.save(existingAccount);
    }
}
