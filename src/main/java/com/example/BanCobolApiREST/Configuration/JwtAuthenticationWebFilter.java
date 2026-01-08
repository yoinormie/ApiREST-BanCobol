package com.example.BanCobolApiREST.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationWebFilter implements WebFilter {

    private final JwtTokenProvider jwtUtil;

    // Rutas que no requieren token
    private final List<String> publicPaths = List.of("/security/login", "/security/register");

    public JwtAuthenticationWebFilter(JwtTokenProvider jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // Si es ruta pública, pasar
        if (publicPaths.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        var claims = jwtUtil.isValidToken(token);
        UsernamePasswordAuthenticationToken auth = null;
        if (!claims) {
            System.out.println("Sin claims");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }else {
            // Crear objeto Authentication con lo que necesites (usuario + roles)
            auth = new UsernamePasswordAuthenticationToken(
                    jwtUtil.getClaims(token).getSubject(),
                    null,
                    List.of()
            );
        }
        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(auth))));
    }
}
