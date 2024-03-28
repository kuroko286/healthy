package com.kuroko.heathyapi.feature.oauth2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.kuroko.heathyapi.enums.CookieName;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.account.model.Role;
import com.kuroko.heathyapi.feature.user.model.CustomUserDetails;
import com.kuroko.heathyapi.feature.user.model.User;
import com.kuroko.heathyapi.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final JwtService jwtService;

    @Value("${app.frontend.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauth2Token.getPrincipal();
            String email = oauth2User.getAttribute("email");
            Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                    "Account with email " + email + " not found."));

            UserDetails userDetails = new CustomUserDetails(account);
            String jwtToken = jwtService.generateToken(userDetails);
            Cookie tokenCookie = new Cookie(CookieName.ACCESS_TOKEN.toString(), jwtToken);
            tokenCookie.setHttpOnly(true);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(60 * 60 * 24 * 365);
            Cookie idCookie = new Cookie(CookieName.USER_ID.toString(), account.getUser().getId().toString());
            idCookie.setPath("/");
            idCookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(tokenCookie);
            response.addCookie(idCookie);
            response.sendRedirect(redirectUri);
        }
    }
}