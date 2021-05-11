package de.cookiebook.restservice.JWT;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class JWTAuthenticationRequest {
    private String email;
    private String password;
}
