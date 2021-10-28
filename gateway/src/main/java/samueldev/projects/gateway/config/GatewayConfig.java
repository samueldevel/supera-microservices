package samueldev.projects.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import samueldev.projects.gateway.filter.AuthFilter;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter).stripPrefix(1))
                        .uri("http://localhost:8083"))

                .route("products", r -> r.path("/products/**")
                        .filters(f -> f.filter(filter).stripPrefix(1))
                        .uri("http://localhost:8082"))

                .build();
    }

}
