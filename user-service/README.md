# User-Service
- 사용자 관련 처리를 담당하는 마이크로 서비스

---

## application.yml
```yaml
server:
  port: 0 # 0으로 지정하면 랜덤 포트로 실행됨

spring:
  application:
    name: user-service # 마이크로서비스 이름

  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver

  h2:
    console:
      enabled: true

  jpa:
    properties:
      hibernate:
        # show_sql: true # sout으로 하이버네이트 실행 SQL을 남기는데 이는 아래의 spring.logging.level.org.hibernate.SQL 옵션에서 log로 대체
        format_sql: true # sql을 로깅할 때 예쁘게 보여줌

logging:
  level:
    org.hibernate.SQL: debug # 하이버네이트 실행 SQL을 logger를 통해 남긴다.
#    org.hibernate.type: trace # 쿼리 parameter의 값을 로그로 남김. 대체재로 p6spy가 있다. 배포환경에서는 사용하지 성능 상 문제가 있다면 사용할지 말지를 고민하는 것이 좋다.

eureka:
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
  client:
    register-with-eureka: true # 유레카 서버에 등록할 것인가? -> true
    fetch-registry: true # 유레카 서버로부터 인스턴스들의 정보를 주기적으로 가져올 것인가 -> true
    eureka-service-url:
      defaultZone: http://127.0.0.1:8761/eureka # 유레카 서비스의 위치, 엔드포인트

greeting:
  message: Welcome to the simple E-commerce.
```
- `server.port: 0` : 실행할 때마다 매 번 랜덤 포트에서 실행하도록 한다. (기존에 존재하는 포트와 겹치지 않는다.)
- `spring.datasource` : datasource 설정
  - `url=jdbc:h2:mem:testdb` : 인메모리 DB
- `spring.h2.console.enable=true` : H2 데이터베이스 웹 콘솔 접근 활성화
- 유레카 설정
  - `eureka.instance.instance-id` : 인스턴스 각각에게 고유한 식별자를 부여하기 위함. 기본 설정을 사용할 경우,
    `server.port:0`이 문제가 되어 중복 인트턴스로 간주되는 문제가 있다.
  - 유레카 서버에 등록하기 위해, register-with-eureka 옵션을 활성화한다.
  - 다른 인스턴스들의 정보를 주기적으로 가져오기 위해 fetch-registry 옵션을 활성화한다.
- 로깅 설정
  - `logging.level.org.hibernate.SQL=debug` : 하이버네이트 실행 SQL을 logger를 통해 남긴다.
  - `spring.jpa.properties.hibernate.format_sql=true` : sql을 로깅할 때 예쁘게 보여줌
- `greeting.message` : 간단한 인사메시지

---

## UserServiceApplication

```java

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
```
- `@EnableDiscoveryClient`
  - 스프링 클라우드에서, 애플리케이션이 Discovery Client 및 스프링 클라우드 로드 밸런서 라이브러리를 사용할 수 있게 함
  - 애플리케이션에서 Discovery 서비스로부터, 서비스 인스턴스를 검색할 수 있게 된다.

---

