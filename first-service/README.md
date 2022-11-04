# First-Service
- Netflix Zuul, Spring Cloud Gateway 실습

---

## application.yml
```yaml
server:
  port: 8081

spring:
  application:
    name: my-first-service

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka

```
- 유레카에 등록, 레지스트리를 fetch해옴

---

## Controller
```java
@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the First Service";
    }


    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String headerValue) {
        log.info("first-request-header : {}", headerValue);
        return "Hello world in First Service.";
    }


    @GetMapping("/check")
    public String check() {
        return "Hi, there. This is a message from First Service.";
    }
}
```
- `/welcome` : 간단하게 first-service 의 api임을 확인할 수 있게 작성함
- `/message` : API 게이트웨이의 사전 필터에서 추가된 header를 확인하기 위함
- `/check` : API 게이트웨이의 커스텀 필터 적용 확인용

---
