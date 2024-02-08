package com.kuroko.heathyapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kuroko.heathyapi.feature.oauth2.CustomOAuth2UserService;
import com.kuroko.heathyapi.feature.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.kuroko.heathyapi.feature.oauth2.OAuth2AuthenticationFailureHandler;
import com.kuroko.heathyapi.feature.oauth2.OAuth2AuthenticationSuccessHandler;
import com.kuroko.heathyapi.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final AuthenticationProvider authenticationProvider;
        private final CustomOAuth2UserService customOAuth2UserService;
        private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
        private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

        @Bean
        public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
                return new HttpCookieOAuth2AuthorizationRequestRepository();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                (authorize) -> authorize.requestMatchers("/v1/auth/**").permitAll()
                                                                .requestMatchers("/chatgpt/**").permitAll()
                                                                .requestMatchers("/oauth2/**").permitAll()
                                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))
                                .oauth2Login(oauth2 -> oauth2
                                                .authorizationEndpoint(
                                                                (endpoint) -> endpoint.baseUri("/oauth2/authorize")
                                                                                .authorizationRequestRepository(
                                                                                                cookieAuthorizationRequestRepository()))

                                                .redirectionEndpoint(
                                                                (endpoint) -> endpoint.baseUri("/oauth2/callback/**"))
                                                .userInfoEndpoint(
                                                                (endpoint) -> endpoint
                                                                                .userService(customOAuth2UserService))

                                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                                .failureHandler(oAuth2AuthenticationFailureHandler))

                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

}
