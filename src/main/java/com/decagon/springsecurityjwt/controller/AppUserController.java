package com.decagon.springsecurityjwt.controller;

import com.decagon.springsecurityjwt.dto.AppUserRequest;
import com.decagon.springsecurityjwt.dto.GetAppUserResponse;
import com.decagon.springsecurityjwt.dto.LoginRequest;
import com.decagon.springsecurityjwt.dto.LoginResponse;
import com.decagon.springsecurityjwt.model.AppUser;
import com.decagon.springsecurityjwt.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse result = appUserService.appUserLogin(loginRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody AppUserRequest appUserRequest) {
        appUserService.createAppUser(appUserRequest);
        return new ResponseEntity<>("user created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetAppUserResponse>> getAllUsers() {
        List<GetAppUserResponse> appUsers = appUserService.findAllUsers();
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }
}
