package de.cookiebook.restservice.JWT;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}
