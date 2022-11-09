package com.ttasjwi.userservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    /**
     * 권한 부여
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 필터 비활성화
        http.authorizeRequests().antMatchers("/users/**").permitAll(); // /users 아래의 모든 url에 접근 허용
        http.headers().frameOptions().disable(); // h2-console html의 프레임별로 데이터가 나누어져 있는 문제. 이를 무시함. h2-console 접근 불가 문제
    }
}
