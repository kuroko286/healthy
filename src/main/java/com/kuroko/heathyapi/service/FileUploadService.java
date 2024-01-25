package com.kuroko.heathyapi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.kuroko.heathyapi.exception.integration.FileStorageException;

@Service
public class FileUploadService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return (String) result.get("url");
        } catch (Exception e) {
            throw new FileStorageException("Failed to store file " + file.getOriginalFilename());
        }
    }
}
