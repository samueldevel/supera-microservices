package samueldev.projects.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import samueldev.projects.core.property.JwtConfiguration;
import samueldev.projects.gateway.util.JwtUtil;
import samueldev.projects.gateway.validator.RouterValidator;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter implements GatewayFilter {
    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;
    private final JwtConfiguration jwtConfiguration;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            final String header = this.getAuthHeader(request);

            if (this.isAuthMissing(request) || !header.startsWith(jwtConfiguration.getHeader().getPrefix()))
                return this.onError(exchange);

            String token = header.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();
            log.info("Authorities: " + jwtUtil.isRoleValid(token));

            if (jwtUtil.isInvalid(token))
                return this.onError(exchange);

        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(jwtConfiguration.getHeader().getName()).get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(jwtConfiguration.getHeader().getName());
    }

}