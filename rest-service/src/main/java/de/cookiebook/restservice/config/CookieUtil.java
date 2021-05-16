package de.cookiebook.restservice.config;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    private CookieCipher cookieCipher;

    private String accessTokenCookieName = System.getenv("LIVE_POLL_JWT_AUTH_COOKIE_NAME");
    private String isTLSEncrypted = System.getenv("LIVE_POLL_SERVER_URL").startsWith("https://");
    private String isDevServer = System.getenv("LIVE_POLL_SERVER_URL").contains("localhost");
    private String domain = if(isDevServer) "localhost" else "live-poll.de";
;
     void createAccessTokenCookie(String token) throws Exception {
         buildCookie(cookieCipher.encrypt(token));
     }
     void deleteAccessTokenCookie(){
         buildCookie("");
     }

    void buildCookie(String content) {
        ResponseCookie.from(accessTokenCookieName, content);
            .maxAge(if (content.isBlank()) 0 else TOKEN_DURATION)
            .httpOnly(true)
                // Secure is only supported with https
                .secure(isTLSEncrypted)
                // Use top level domain. Subdomains are included automatically (https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie)
                .domain(domain)
                .sameSite("None")
                .path("/")
                .build()
    }
}
