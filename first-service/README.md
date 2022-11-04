# First-Service
- Netflix Zuul, Spring Cloud Gateway 실습

---

## application.yml
```yaml
server:
  port: 0

spring:
  application:
    name: my-first-service

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
```
- 매번 랜덤 포트에 바인딩하여 실행하고, 인스턴스 명을 랜덤하게 생성한다.
- 유레카에 등록, 레지스트리를 fetch해옴

---

## Controller
```java

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    private final Environment env;

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
    public String check(HttpServletRequest request) {
        log.info("Server port = {}", request.getServerPort());
        return String.format(
                "Hi, there. This is a message from First Service. %s",
                env.getProperty("local.server.port")
        );
    }
}
```
- `/check` : 요청이 들어왔을 때, 서버 포트를 로깅하고 api 응답으로 서버 포트번호를 포함하여 응답한다.


---
