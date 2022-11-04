
# ApiGateway-Service

- Spring Cloud Gateway를 사용하여 API 게이트웨이 역할
- 스프링부트 2.4 이후 권장되는 사용 방식

---

## application.yml
```yaml
server:
  port: 8000

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka

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
      routes:
        - id: first-service
          uri: lb://MY-FIRST-SERVICE
          predicates: # 조건
            - Path=/first-service/** # 주의 : uri 뒤에 붙어서 포워딩 됨. (예: http://localhost:8081/firstservice/** )
          filters: # 조건에 부합한다면, 적용할 부가 로직
#            - AddRequestHeader=first-request, first-request-header-value2
#            - AddResponseHeader=first-response, first-response-header-value2
            - CustomFilter
        - id: second-service
          uri: lb://MY-SECOND-SERVICE
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
- Service Discovery에 gateway 등록
- routes에서 라우팅 uri를 `lb://(디스커버리에 등록된 서비스명)`으로 지정하여 서비스 이름으로 라우팅

---
