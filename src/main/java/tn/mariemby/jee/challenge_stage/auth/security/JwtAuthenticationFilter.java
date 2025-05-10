package tn.mariemby.jee.challenge_stage.auth.security;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.mariemby.jee.challenge_stage.auth.services.JwtService;
import tn.mariemby.jee.challenge_stage.users.dtos.UserDto;
import tn.mariemby.jee.challenge_stage.users.entities.User;
import tn.mariemby.jee.challenge_stage.users.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            if (jwtService.isTokenValid(jwt) && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDto userDto = jwtService.decodeToken(jwt);
                if (userDto==null){
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                    return;
                }
                User user= userService.getUserByEmail(userDto.getEmail());
                if (user==null){
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found.");
                    return;
                }
                List<String> roles = List.of(user.getRole().name());
                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, jwt, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
