# Second-Service
- Netflix Zuul, Spring Cloud Gateway 실습

---

## application.yml
```yaml
server:
  port: 8082

spring:
  application:
    name: my-second-service

eureka:
  client:
    register-with-eureka: false # 유레카에 등록 x
    fetch-registry: false # 유레카로부터 레지스트리 정보를 주기적으로 fetch 안 함
```
- 8082번 포트에 바인딩
- 당장은 유레카 서비스에 등록하지 않을 예정이므로, register-with-eureka 을 false로 함
- 유레카로부터 레지스트리 정보를 주기적으로 가져오지 않을 것이므로, fetch-registry 을 false로 함

---

## Controller
```java
@Slf4j
@RestController
@RequestMapping("/second-service")
public class SecondServiceController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Second Service";
    }


    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String headerValue) {
        log.info("second-request-header : {}", headerValue);
        return "Hello world in Second Service.";
    }

}
```
- `/welcome` : 간단하게 second-service 의 api임을 확인할 수 있게 작성함
- `/message` : API 게이트웨이의 사전 필터에서 추가된 header를 확인하기 위함

---
