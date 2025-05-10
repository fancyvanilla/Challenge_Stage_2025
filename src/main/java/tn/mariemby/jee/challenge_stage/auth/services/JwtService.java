package tn.mariemby.jee.challenge_stage.auth.services;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import tn.mariemby.jee.challenge_stage.users.entities.Role;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static java.lang.Long.parseLong;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long jwtExpiration;

    public String createJwtToken(UserDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nom", user.getNom());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public UserDto decodeToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new UserDto(
                    parseLong(claims.getSubject()),
                    claims.get("nom", String.class),
                    claims.get("email", String.class),
                    Role.valueOf(claims.get("role", String.class))
            );
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
        }
        return null;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}