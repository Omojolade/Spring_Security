package com.decagon.springsecurityjwt.service.impl;

import com.decagon.springsecurityjwt.dto.AppUserRequest;
import com.decagon.springsecurityjwt.dto.GetAppUserResponse;
import com.decagon.springsecurityjwt.dto.LoginRequest;
import com.decagon.springsecurityjwt.dto.LoginResponse;
import com.decagon.springsecurityjwt.model.AppUser;
import com.decagon.springsecurityjwt.repository.AppUserRepository;
import com.decagon.springsecurityjwt.security.JwtTokenProvider;
import com.decagon.springsecurityjwt.service.AppUserService;
import com.decagon.springsecurityjwt.utils.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.decagon.springsecurityjwt.utils.AppConstants.TOKEN_PREFIX;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, AuthenticationManager authenticationManager,
                              JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAppUser(AppUserRequest appUserRequest) {
        try {
            AppUser appUser = ModelMapper.INSTANCE.convertAppUserRequestToAppUserEntity(appUserRequest);// the mapper sets the email and password in the database
            appUser.setEnabled(true);
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

//            AppUser appUser = new AppUser();
//            appUser.setPassword(passwordEncoder.encode(appUserRequest.getPassword())); // this done so as to encode the password
//            appUser.setEmail(appUserRequest.getEmail());
//            appUser.setEnabled(true);
            appUserRepository.save(appUser);

        } catch (Exception e) {
            //TODO: throw proper exception here
            e.printStackTrace();
        }
    }

    @Override
    public LoginResponse appUserLogin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoginResponse(true, TOKEN_PREFIX +
                jwtTokenProvider.generateAppUserJwtToken(authentication));
    }

    public List<GetAppUserResponse> findAllUsers() {
        //TODO return a List<AppUserResponse> type
        List<AppUser> appUsers = appUserRepository.findAll();

        return appUsers.stream().map(user -> new GetAppUserResponse(user.getEmail(), user.getCreatedAt()))
                .collect(Collectors.toList());

//     return  appUsers.stream().map(user -> {
//          GetAppUserResponse getAppUserResponse = new GetAppUserResponse(user.getEmail(), user.getCreatedAt());
//          return getAppUserResponse;
//      }).collect(Collectors.toList());
    }
}
