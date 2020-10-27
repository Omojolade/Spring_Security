package com.decagon.springsecurityjwt.utils;

public class AppConstants {

    public static final long EXPIRATION_TIME = 18000_000;
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SIGNUP_URL = "/user/register";
    public static final String LOGIN_URL = "/user/login";
    public static final String H2_CONSOLE = "/h2/**";
}
