# Zuul Service

- API 게이트웨이 역할
- Spring Boot 2.4 이후 Maintenance 상태 (이후 버전에서는 Spring Cloud Gataway 를 사용하는 것을 권장)

---

## application.yml
```yaml
server:
  port: 8000

spring:
  application:
    name: my-zuul-service

zuul:
  routes:
    first-service:
      path: /first-service/**
      url: http://localhost:8081
    second-service:
      path: /second-service/**
      url: http://localhost:8082
```
- 8000번 포트에 바인딩
- `http://localhost:8000/first-service/...` 로 들어온 요청을 `http://localhost:8081/...` 으로 포워딩한다.
- `http://localhost:8000/second-service/...` 로 들어온 요청을 `http://localhost:8082/...` 으로 포워딩한다.

---

## ZuulServiceApplication
```java
@SpringBootApplication
@EnableZuulProxy
public class ZuulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceApplication.class, args);
    }

}
```
- Zuul 프록시를 활성화하기 위해 `@EnableZuulProxy`를 적용

---
