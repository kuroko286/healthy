package com.kuroko.heathyapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kuroko.heathyapi.feature.oauth2.CustomOAuth2UserService;
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
        @Value("${app.frontend.url}")
        private String frontendUrl;
        @Autowired
        private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
        @Autowired
        private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
        @Autowired
        private CustomOAuth2UserService customOAuth2UserService;

        /*
         * Oauth2 flow in this spring config: The authorization request is sent from
         * frontend using
         * authorizationEndpoint, after successful authentication, oauth2 provider (like
         * google) return access token, then spring boot use it to fetch userinfo from
         * userInfoEndpoint, using
         * CustomOAuth2UserService to process this info and create a user (if not
         * exist), then
         * last the success handler will be called to send cookie and redirect to
         * frontend.
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                (authorize) -> authorize.requestMatchers("/v1/auth/**").permitAll()
                                                                .requestMatchers("/chatgpt/**").permitAll()
                                                                .requestMatchers("/oauth2/**").permitAll()
                                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))
                                .oauth2Login((oauth2) -> oauth2.authorizationEndpoint(
                                                authorization -> authorization.baseUri("/oauth2/authorize"))
                                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                                .failureHandler(oAuth2AuthenticationFailureHandler)
                                                .userInfoEndpoint((userInfo) -> userInfo
                                                                .userService(customOAuth2UserService)))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of(frontendUrl));
                configuration.addAllowedHeader("*");
                configuration.addAllowedMethod("*");
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
                urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
                return urlBasedCorsConfigurationSource;
        }

}
