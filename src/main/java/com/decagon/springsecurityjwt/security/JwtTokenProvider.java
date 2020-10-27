package com.decagon.springsecurityjwt.security;

import com.decagon.springsecurityjwt.model.AppUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.decagon.springsecurityjwt.utils.AppConstants.EXPIRATION_TIME;

@Component
public class JwtTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("$(app.token-secret)")
    private String SECRET;

    public String generateAppUserJwtToken(Authentication authentication) {
        try {
            AppUser appUser = (AppUser) authentication.getPrincipal();
            return buildJwt(appUser.getEmail());

        } catch (Exception exception) {
            //TODO throw a proper exception here saying invalid credentials,
            // and the status should be a bad request.
            return null;
        }
    }

    public String buildJwt(String email) {
        Date now = new Date(System.currentTimeMillis());
        Date expireDate = new Date(now.getTime() + EXPIRATION_TIME);
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * This method gets the email from a token
     *
     * @param token token
     * @return
     */
    public String getEmailFromJwtToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody().get("email");
    }

    /**
     * This method validates our jwt token
     *
     * @param authToken token
     * @return a boolean
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        }
        catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
