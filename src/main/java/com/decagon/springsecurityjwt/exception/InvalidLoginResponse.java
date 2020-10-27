package com.decagon.springsecurityjwt.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String message;
    private String error;

    public InvalidLoginResponse() {
        this.message = "Invalid Credentials";
        this.error = "Full authentication is required to access this resource";
    }
}
