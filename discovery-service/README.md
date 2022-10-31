
# Discovery-Service
서비스 디스커버리 역할

---

## application.yml
```yaml
server:
  port: 8761

spring:
  application:
    name: discoveryService # 마이크로서비스 이름 -> 실제 등록은 대소문자 구분 없이 대문자로 등록됨

eureka:
  client:
    register-with-eureka: false # 유레카 서버 자기자신을 등록할 것인가? -> false
    fetch-registry: false # 유레카 서버로부터 인스턴스들의 정보를 주기적으로 가져올 것인가 -> false

```
- 환경 설정 파일이므로 코드 베이스(github)에 push 하면 안 됨
  - 외부 설정 파일은 코드베이스에 포함시키면 안 된다.
- 설정 설명
    - `server.port` : 유레카 서버의 포트
    - `spring.application.name` : 해당 서비스의 이름
    - `eureka.client.register-with-eureka` : 유레카 서버에 해당 서비스를 등록할 것인가?
    - `eureka.client.fetch-registry`
        - 클라이언트 소프트웨어는 30초마다 유레카 서비스에 레지스트리 변경 사항을 확인
        - 레지스트리를 검색할 때마다, 유레카 서비스를 호출하는 대신 레지스트리를 로컬에 캐싱
        - 30초마다 갱신된 정보를 받아 로컬에 캐싱한다.

---

## DiscoveryServiceApplication
```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }

}
```
- `@EnableEurekaServer` : 스프링 서버에서 유레카 서버 활성화

---
