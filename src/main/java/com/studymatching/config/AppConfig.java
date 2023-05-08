package com.studymatching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCryptPasswordEncoder 암호화 설정
        //but 좀 느라다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}


