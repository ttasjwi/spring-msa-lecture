
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
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
```
- 8000번 포트로 바인딩
- `http://호스트 주소:8000/first-service/**` 요청을 `http://localhost:8081/first-service/**`로 포워딩한다
- `http://호스트 주소:8000/second-service/**` 요청을 `http://localhost:8082/second-service/**`로 포워딩한다
    - zuul과 차이점은, Path로 지정한 부분이 uri 뒤에 붙어서 포워딩된다는 것이다.
- 기존 서비스들이 Zuul을 사용했었다면, 이에 맞춰 해당 서비스들의 맵핑 url을 변경해야한다.

---
