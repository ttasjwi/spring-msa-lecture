package com.ttasjwi.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    public static class Config {
        // Put the configuration properties : 설정 정보가 필요하면 내부적으로 정의할 수 있음
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Cutsom Pre Filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest(); // 서블릿 방식과 달리, WebFlux에서는 ServerHttpRequest/Response를 사용
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom Pre filter: requestId = {}", request.getId());

            return chain
                    .filter(exchange) // 다음 필터에 위임
                    .then(Mono.fromRunnable( // Custom Post Filter
                            () -> log.info("Custom Post filter: response code = {}", response.getStatusCode())
                    ));
        };
    }
}
