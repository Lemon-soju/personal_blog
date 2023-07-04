
### 할일
- 예외처리 리팩토링
- 좋아요 기능 && 게시글에 좋아요 받은 수 표시

## API Docs
https://lemonsoju.blog:8080/docs/index.html

### 환경 변수 설정

- 프로젝트 루트 디렉터리에 `application.yml` 파일 생성
- `application.yml` 예시
- 
```
spring:
  datasource:
    url: {POSTGRESQL_주소}
    username: {사용자명}
    password: {비밀번호}
    show-sql: true
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
        use_sql_comments: true
    show_sql: true


  logging.level:
    org.hibernate.SQL: debug
    # org.hibernate.type: trace

  server:
    servlet:
      session:
        tracking-modes: cookie

cloud:
  aws:
    s3:
      bucket: {버킷명}
    credentials:
      access-key: {접근키}
      secret-key: {비밀키}
    region:
      static: ap-northeast-2
      auto: false
    stack:
      auto: false
```

### 실행

- 패키시 설치

```
npm install
```

- `nest` 서버 실행

```
npm start
```

## DB Schema
<img src="img/entity.png">

- `user` : 사용자 계정 정보
- `post` : 글 정보

## 배포

- `ec2` 인스턴스 생성하여 운영
- `AWS rds` 서비스를 통해 `postgresql` 데이터베이스 운영
- `nohup` 을 사용하여 `Java Spring` 서버를 백그라운드 프로세스로 관리
- `AWS Certificate Manager, Route 53, 로드밸런서` 를 통해 `https` 적용

## 브랜치 관리

1. `test` 브랜치에서 개발 작업
2. 자체 QA 검증 후 main 브랜치와 merge
