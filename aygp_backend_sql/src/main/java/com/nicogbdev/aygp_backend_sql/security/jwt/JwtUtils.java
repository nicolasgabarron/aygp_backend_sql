package com.nicogbdev.aygp_backend_sql.security.jwt;

import com.nicogbdev.aygp_backend_sql.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {

    // Propiedades
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${nicogbdev.app.jwtSecret}")
    private String jwtSecret;
    @Value("${nicogbdev.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${nicogbdev.app.jwtCookieName}")
    private String jwtCookie;

    // Métodos.
    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        if (cookie != null) {
            return cookie.getValue();
        }else
            return null;
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl usuario){
        String jwt = generateTokenFromUsername(usuario.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60). httpOnly(true).build();

        return cookie;
    }

    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            logger.error("Firma del JWT inválida: {}", e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Token JWT inválido: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("El token JWT no está soportado: {}", e);
        }catch (IllegalArgumentException e){
            logger.error("No se ha especificado el token JWT: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

}
