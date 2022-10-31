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
    register-with-eureka: false # 유레카에 등록 x
    fetch-registry: false # 유레카로부터 레지스트리 정보를 주기적으로 fetch 안 함
```
- 8081번 포트에 바인딩
- 당장은 유레카 서비스에 등록하지 않을 예정이므로, register-with-eureka 을 false로 함
- 유레카로부터 레지스트리 정보를 주기적으로 가져오지 않을 것이므로, fetch-registry 을 false로 함

---

## 컨트롤러
```java

@RestController
@RequestMapping("/")
public class FirstServiceController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the First Service";
    }

}
```
- `http://localhost:8081/welcome`
- 간단하게 first-Service의 api임을 확인할 수 있게 작성함

---
