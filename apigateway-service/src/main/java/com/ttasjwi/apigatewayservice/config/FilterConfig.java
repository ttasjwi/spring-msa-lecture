package com.ttasjwi.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

//@Configuration
public class FilterConfig {

    //@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("first-service",
                        request -> request.path("/first-service/**")
                                        .filters(filters -> filters.addRequestHeader("first-request", "first-request-header-value")
                                                                    .addResponseHeader("first-response", "first-response-header-value"))
                                        .uri("http://localhost:8081"))
                .route("second-service",
                        request -> request.path("/second-service/**")
                                        .filters(filters -> filters.addRequestHeader("second-request", "second-request-header-value")
                                                                    .addResponseHeader("second-response", "second-response-header-value"))
                                        .uri("http://localhost:8082"))
                .build();
    }
}
