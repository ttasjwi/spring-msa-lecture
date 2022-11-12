## Order-Service

- 사용자 주문 처리

---

## application.yml
```yaml
server:
  port: 0 # 0으로 지정하면 랜덤 포트로 실행됨

spring:
  application:
    name: order-service # 마이크로서비스 이름

  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver

  h2:
    console:
      enabled: true

  jpa:
    hibernate:
      ddl-auto: update
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

```

---
