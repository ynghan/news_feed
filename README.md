# news\_feed

### 프로젝트 소개

사용자들 사이에 일상에서 흥미로운 요소들을 공유하고 즐길 수 있는 멀티 파일 업로드 기능을 제공하는 웹사이트

### 기술 스택

Java, Spring Boot, Spring Data JPA, Spring Security, MySQL, JWT, AWS S3, Mockito, Thymeleaf

### 전체 시스템 구조

![news\_feed\_system](https://github.com/ynghan/news\_feed/assets/119781387/2d8383b3-fbc6-45e4-9b22-d72365d60fc1)

### 데이터베이스 구조

![image](https://github.com/ynghan/news\_feed/assets/119781387/1d20d342-6fb2-4191-aef0-fd1838047918)

### 주요 기능

1. 사용자 인증/인가 기능
2. CRUD 기능
3. S3를 사용한 멀티 파일 업로드 기능
4. AOP 기능
5. 단위 테스트 기능

### API 명세서

[API 명세서](https://documenter.getpostman.com/view/32531805/2sA2xccFim#dce2d61a-8708-4e39-82a7-7ce451e1bb27)

### Jpa LocalDateTime 자동으로 생성하는 법

* @EnableJpaAuditing (Main 클래스)
* @EntityListeners(AuditingEntityListener.class) (Entity 클래스)

```java
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
```
