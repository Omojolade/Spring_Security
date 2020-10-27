package com.decagon.springsecurityjwt.setup;

import com.decagon.springsecurityjwt.dto.LoginRequest;
import com.decagon.springsecurityjwt.security.JwtTokenProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.decagon.springsecurityjwt.utils.AppConstants.TOKEN_PREFIX;

@Profile("test")
@Service
public class GetJwt {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public GetJwt(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public String generateUserToken(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        return TOKEN_PREFIX + jwtTokenProvider.generateAppUserJwtToken(authentication);
    }
}
