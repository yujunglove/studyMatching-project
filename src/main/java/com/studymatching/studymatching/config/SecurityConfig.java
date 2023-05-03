package com.studymatching.studymatching.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //내가 원하는 특정한 요청들은 인증 체크를 하지 않도록 만든다.
        http.authorizeHttpRequests()
                .mvcMatchers("/","/login","/sign-up","/check-email","/check-email-token," +
                        "/email-login","/check-email-login","/login-link").permitAll()
                .mvcMatchers(HttpMethod.GET,"/profile/*").permitAll()
                //나머지는 로그인 해야만 쓸수 있음
                .anyRequest().authenticated();
    }
}
