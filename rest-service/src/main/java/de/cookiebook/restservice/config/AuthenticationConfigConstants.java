package de.cookiebook.restservice.config;

public class AuthenticationConfigConstants {
    public static final String SECRET = "My_Cookie_Book_secret";
    public static final long EXPIRATION_TIME = 10800000; // 3h = 10800000
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/login";
}
