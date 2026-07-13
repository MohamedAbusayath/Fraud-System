package com.example.fraudsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwt;

    public JwtAuthenticationFilter(JwtTokenValidator jwt){
        this.jwt=jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header==null || !header.startsWith("Bearer ")){
            sendUnauthorizedResponse(
                    response,
                    "Authorization token is missing"
            );
            return;
        }
        String token = header.substring(7);
        try{
            Claims claims = jwt.validationToken(token);
            String sub = claims.getSubject();
            String role = claims.get("role", String.class);
            if(!"payment-service".equals(sub) || !"SERVICE".equals(role)){
                sendUnauthorizedResponse(
                        response,
                        "Unauthorized service"
                );
                return;
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            sub,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + role
                                    )
                            )
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException e){
            sendUnauthorizedResponse(
                    response,
                    "Service JWT expired"
            );
        }
        catch (JwtException ex){
            sendUnauthorizedResponse(
                    response,
                    "Invalid service JWT"
            );
        }

    }

    private void sendUnauthorizedResponse(HttpServletResponse res,String msg) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.getWriter().write(
                """
                        {
                          "Status":401,
                          "Message":"%s"
                        }
                        """.formatted(msg)
        );
    }
}
