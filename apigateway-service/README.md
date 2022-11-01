
# ApiGateway-Service

- Spring Cloud Gateway를 사용하여 API 게이트웨이 역할
- 스프링부트 2.4 이후 권장되는 사용 방식

---

## 주의 : Netty 서버 사용
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
```
- Spring Cloud Gateway는 톰캣이 아닌, Netty 서버를 내장함

---

## application.yml
```yaml

## application.yml을 통해 Filter 등록
```yaml
server:
  port: 8000

eureka:
  client:
    register-with-eureka: false # 나중에 등록할 예정
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:8761/eureka

spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      routes:
        - id: first-service
          uri: http://localhost:8081/ # 어디로 포워딩
          predicates: # 조건
            - Path=/first-service/** # 주의 : uri 뒤에 붙어서 포워딩 됨. (예: http://localhost:8081/firstservice/** )
          filters: # 조건에 부합한다면, 적용할 부가 로직
            - AddRequestHeader=first-request, first-request-header-value2
            - AddResponseHeader=first-response, first-response-header-value2
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
          filters: # 조건에 부합한다면 적용할 부가 로직
            - AddRequestHeader=second-request, second-request-header-value2
            - AddResponseHeader=second-response, second-response-header-value2
```
- 8000번 포트로 바인딩
- `http://호스트 주소:8000/first-service/**` 요청을 `http://localhost:8081/first-service/**`로 포워딩한다
- `http://호스트 주소:8000/second-service/**` 요청을 `http://localhost:8082/second-service/**`로 포워딩한다
    - zuul과 차이점은, Path로 지정한 부분이 uri 뒤에 붙어서 포워딩된다는 것이다.
- 기존 서비스들이 Zuul을 사용했었다면, 이에 맞춰 해당 서비스들의 맵핑 url을 변경해야한다.

---

## (참고) java 코드로 Gateway Route 정의 및 Filter 등록
```java

@Slf4j
@Configuration
public class FilterConfig {

    @Bean
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
```
- 이 방식을 사용하려면, 위의 yml에서 `cloud.gateway.routes` 부분 주석처리해야한다.
- 자바코드로 RouteLocator를 build한 뒤 스프링 빈으로 등록할 있다.
- 고유한 id, 매칭 조건, 필터(사전/사후), 목적 uri를 지정할 수 있다.
- java 코드로 설정하지 않고 yml로 설정하려면 `@Configuration`, `@Bean` 부분을 주석처리하고 yml로 설정을 등록하면 된다.

---

## 커스텀 필터 정의 및 등록
```java
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
```
- 여기서 기본생성자를 통해 스프링 빈이 생성되어 등록된다.
- `AbstractGatewayFilterFactory<CustomFilter.Config>`를 상속해야함
  - `apply(Config config)`을 구현
  - `Config`은 내부 static 클래스로 정의하면 되는데, 설정정보가 필요하면 내부적으로 정의하면 된다.
- exchange, chain을 인자로 하여, GatewayFilter를 반환하는 필터를 반환하는 함수를 반환하면 된다.
  - 이 함수의 return문 앞에서, 사전 필터 로직을 구현하면 된다.
  - exchange : ServerWebExchange (Http 요청-응답 상호작용을 위한 계약으로, Spring 5 이후 지원되었다. request, response에 대한 액세스 제공 등 수행)
  - chain : GatewayFilterChain (다음 filter 체인에 위임)
  - `chain.filter(...)` : 다음 필터에 위임
  - `then(...)` : 사후 필터

### application.yml
```yaml
          filters: # 조건에 부합한다면, 적용할 부가 로직
#            - AddRequestHeader=second-request, second-request-header-value2
#            - AddResponseHeader=second-response, second-response-header-value2
            - CustomFilter
```
- 기존 필터를 주석처리하고 CutstomFilter를 그대로 등록했다. 등록된 빈을 찾아서 사용하는 것으로 추정됨

---

## 글로벌 필터 적용
```java
@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Getter
    public static class Config {
        private final String baseMessage;
        private final boolean preLogger;
        private final boolean postLogger;

        public Config(String baseMessage, boolean preLogger, boolean postLogger) {
            this.baseMessage = baseMessage;
            this.preLogger = preLogger;
            this.postLogger = postLogger;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Global Filter Start : request id = {}", request.getId());
            }

            return chain
                    .filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        if (config.isPostLogger()) {
                            log.info("Global Filter End : response code = {}", response.getStatusCode());
                        }
                    }));

        };
    }
}
```
- 위의 방식대로 필터를 만든다.
- Config 클래스를 만들 때 setter를 열어두거나, 모든 프로퍼티를 정의할 수 있는 생성자를 만들어 사용할 수 있다.

## application.yml
```yaml
spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      default-filters:
        - name: GlobalFilter
          args:
            baseMessage: Spring Cloud Gateway GlobalFilter
            preLogger: true
            postLogger: true
```
- default-filters 에서, 글로벌로 적용할 필터를 지정할 수 있다.
- 이 필터는 조건에 상관없이 모든 사전 피터 앞, 모든 사후 필터 후에 작동한다.
- args에서 Config의 인자들을 지정할 수 있다.

---

## Logging Filter
```java

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = new OrderedGatewayFilter(
                (exchange, chain) -> {
                    ServerHttpRequest request = exchange.getRequest();
                    ServerHttpResponse response = exchange.getResponse();

                    log.info("Logging Filter baseMessage : {}", config.getBaseMessage());

                    if (config.isPreLogger()) {
                        log.info("Logging PRE Filter : request id = {}", request.getId());
                    }

                    return chain
                            .filter(exchange)
                            .then(Mono.fromRunnable(() -> {
                                if (config.isPostLogger()) {
                                    log.info("Logging Post Filter : response code = {}", response.getStatusCode());
                                }
                            }));
                }
                , Ordered.HIGHEST_PRECEDENCE);

        return filter;
    }
}

```
- 반환 타입을 람다 방식으로 구현하면 우선순위를 별도로 지정할 수 없는데, OrderedGatewayFilter를 구현체로 하여 작성하면,
우선순위를 작성자 임의로 정할 수 있다. 이 방식에서는 `Ordered.HIGHEST_PRECEDENCE`를 통해, 제일 upstream에 위치하도록 했다.
  - 이렇게 하면, 사전 필터 중에서는 제일 먼저 실행되고, 사후 필터 중에서는 제일 마지막에 실행된다.

### application.yml
```yaml
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
          filters: # 조건에 부합한다면, 적용할 부가 로직
#            - AddRequestHeader=second-request, second-request-header-value2
#            - AddResponseHeader=second-response, second-response-header-value2
            - name: CustomFilter
            - name: LoggingFilter
              args:
                baseMessage: Hi, there.
                preLogger: true
                postLogger: true
```
- 복수의 필터를 적용하기 위해서 filters에서 name 필드로 별도로 구분하게 했다.
- args에서 Filter 생성시 넘길 인자들을 지정할 수 있다.
- Logging Filter를 Second-Service에만 적용

---
