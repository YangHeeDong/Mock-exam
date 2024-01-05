package com.example.Mockexam.global.app;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Getter
    public static String jwtSecretKey;

    @Value("${custom.jwt.secret}")
    public void setJwtSecretKey(String jwtSecretKey){
        AppConfig.jwtSecretKey = jwtSecretKey;
    }
}
