package com.ttasjwi.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttasjwi.userservice.web.dto.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    /**
     * 1. 로그인 요청을 Spring Security가 이해할 수 있는 토큰으로 변환(id, pw, 권한)
     * 2. 토큰을 전달하여, 사용자 인증 작업을 처리하는 Authentication을 만들어 반환
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest cred = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            Authentication token = new UsernamePasswordAuthenticationToken(
                    cred.getEmail(), // id
                    cred.getPassword(), // pw
                    new ArrayList<>()); // 권한

            Authentication authentication = getAuthenticationManager().authenticate(token);

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 로그인이 성공 시 어떤 작업을 할 것인지 정의
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //TODO : 인증 성공 후 로직
    }
}
