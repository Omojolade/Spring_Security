package com.decagon.springsecurityjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GetAppUserResponse {
    private String email;
    private LocalDateTime createdAt;
}
