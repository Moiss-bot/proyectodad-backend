package pe.edu.upeu.apigateway.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final String SECRET = "clavesecretasupersegura321upeu2026";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.contains("/auth/")) {
            return chain.filter(exchange);
        }
        
        String auth = exchange.getRequest().getHeaders().getFirst("Authorization");

        // Sin token → 401
        if (auth == null || !auth.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"error\":\"No autorizado\",\"mensaje\":\"Token ausente o inválido. Debe autenticarse primero.\"}";
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        // Validar token
        try {
            String token = auth.substring(7);
            String rol = extractRol(token);
            String method = exchange.getRequest().getMethod().name();

            // Si es USER y quiere POST, PUT o DELETE → 403
            if ("USER".equals(rol) && (method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                String body = "{\"error\":\"Acceso denegado\",\"mensaje\":\"No tiene permisos para realizar esta operación. Solo el ADMIN puede ejecutar esta acción.\"}";
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }

            return chain.filter(exchange); // Token válido → pasa

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"error\":\"No autorizado\",\"mensaje\":\"Token ausente o inválido. Debe autenticarse primero.\"}";
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }

    private String extractRol(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("rol", String.class);
    }

    @Override
    public int getOrder() { return -1; }
}