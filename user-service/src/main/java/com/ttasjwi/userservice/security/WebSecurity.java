package com.ttasjwi.userservice.security;

import com.ttasjwi.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final UserDetailsService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 권한 부여
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 필터 비활성화

        http.authorizeRequests()
                //.antMatchers("/user-service/users/**").permitAll(); // /users 아래의 모든 url에 접근 허용
                        .antMatchers("/**")
                        .hasIpAddress("192.168.56.1") // 내 IP
                        .and()
                        .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable(); // h2-console html의 프레임별로 데이터가 나누어져 있는 문제. 이를 무시함. h2-console 접근 불가 문제
    }

    private Filter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
