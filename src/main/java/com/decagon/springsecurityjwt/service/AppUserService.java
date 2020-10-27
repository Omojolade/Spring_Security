package com.decagon.springsecurityjwt.service;

import com.decagon.springsecurityjwt.dto.AppUserRequest;
import com.decagon.springsecurityjwt.dto.GetAppUserResponse;
import com.decagon.springsecurityjwt.dto.LoginRequest;
import com.decagon.springsecurityjwt.dto.LoginResponse;

import java.util.List;

public interface AppUserService {
    /**
     * This method creates a new user
     *
     * @param appUserRequest user request
     * @author Omolade
     */
    void createAppUser(AppUserRequest appUserRequest);

    LoginResponse appUserLogin(LoginRequest loginRequest);

    List<GetAppUserResponse> findAllUsers();
}
