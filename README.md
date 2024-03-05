# news_feed
사용자들 사이에 일상에서 흥미로운 요소들을 공유하고 즐길 수 있는 멀티 파일 업로드 기능을 제공하는 웹사이트

### 프로젝트 소개
news_feed 프로젝트는 

### 전체 시스템 구조
![news_feed_system.png](..%2F..%2F..%2FDocuments%2Fnews_feed_system.png)
### 데이터베이스 구조
![newsfeed_database(1).png](..%2Fimage%2Fnewsfeed_database%281%29.png)
### api 명세서
[API 명세서](https://documenter.getpostman.com/view/32531805/2sA2xccFim#dce2d61a-8708-4e39-82a7-7ce451e1bb27)

### 기술 스택





### Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
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

