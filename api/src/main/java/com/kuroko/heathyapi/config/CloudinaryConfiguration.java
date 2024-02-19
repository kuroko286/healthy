package com.kuroko.heathyapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfiguration {
    @Value("${app.cloudinary.cloud-name}")
    private String cloudName;
    @Value("${app.cloudinary.api-key}")
    private String apiKey;
    @Value("${app.cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpnifdvih",
                "api_key", "358917863795617",
                "api_secret", "Wxs6cCFc-EEo8TPmWn23sixn9w8",
                "secure", true));
        return cloudinary;

    }
}
