package com.decagon.springsecurityjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class LoginRequest {

    @Email(message = "please enter a valid email")
    @NotBlank(message = "please enter a valid email")
    private String email;

    private String password;
}
