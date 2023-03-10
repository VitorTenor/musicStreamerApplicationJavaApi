package com.music.musicStreamer.core.token.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicStreamer.api.v1.models.dtos.UserLoginDTO;
import com.music.musicStreamer.core.token.data.UserDataDetail;
import com.music.musicStreamer.exceptions.SecurityException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRACAO = 999_000_000;
    public static final String TOKEN_SENHA = "abnidfndsjkcmdcjkvcklndda";

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/getToken");
    }



    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)  {
        try {
            UserLoginDTO user = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginDTO.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    new ArrayList<>()
            ));

        } catch (IOException e) {
            throw new SecurityException("Fail to authenticate user");
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDataDetail userData = (UserDataDetail) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}















