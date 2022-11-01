
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
