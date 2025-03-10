package com.karot.ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    public String saveImageToCloud(MultipartFile photo){
        try {
            Map r =  cloudinary.uploader().upload(photo.getBytes(), ObjectUtils.asMap("resource_type","auto"));
            return (String) r.get("secure_url");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to upload image to cloud" + e.getMessage());
        }
    }

}
