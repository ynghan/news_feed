# news_feed
사용자들 사이에 일상에서 흥미로운 요소들을 공유하고 즐길 수 있는 커뮤니티 사이트

## 프로젝트 설명
### 개발 환경
* 프로그래밍 언어: Java 17
* 빌드 툴: Gradle
* 프레임워크: SpringBoot 3.2.2

<br>

### 기술 스택
<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[//]: # (![Html]&#40;https://img.shields.io/badge/Html-E34F26?style=for-the-badge&logo=html5&logoColor=white&#41;)
![Amazon S3](https://img.shields.io/badge/Amazon%20S3-F05138?style=for-the-badge&logo=amazon%20s3&logoColor=white)
![Docker-Compose](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Css](https://img.shields.io/badge/Css-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-2496ED?style=for-the-badge&logo=querydsl&logoColor=white)
![SpringJpa](https://img.shields.io/badge/SpringJpa-6DB33F?style=for-the-badge&logo=jpa&logoColor=white)
![Java](https://img.shields.io/badge/Java-0769AD?style=for-the-badge)

<br>

### 전체 시스템 구조
<img alt="news_feed 전체 구성도" src="https://github.com/ynghan/news_feed/assets/119781387/7eda7c8a-81e2-4920-940c-eb7d291e0076" width="600" height="450">

<br>
<br>

### 데이터베이스 구조
<img alt="ERD 구성도" src="https://github.com/ynghan/news_feed/assets/119781387/e16cec02-8d08-4bc1-a5d4-488b8a06c4a7" width="600" height="450">

<br>

### api 명세서([보러가기](https://documenter.getpostman.com/view/32531805/2sA2xccFim#dce2d61a-8708-4e39-82a7-7ce451e1bb27))

<br>

### 주요 기능

* 회원가입 및 로그인
  * 패스워드 암호화, JWT 토큰 발급과 Spring Security를 통한 인증/인가

* 마이 페이지
  * 패스워드 이력 관리, AWS S3 이용한 프로필 이미지 저장

* 게시글
  * AWS S3를 이용한 멀티미디어 파일 저장

* 백오피스 회원관리
  * 회원 권한 관리, 강제 탈퇴, 게시물 수정 및 삭제 기능

* 댓글 / 좋아요(게시글, 댓글) / 팔로우

<br>

### 성능 개선
* Redis 캐싱을 사용한 조회 API 처리량 개선
  * TPS : 414.3 -> 509.4 (약 22.95% 성능 개선)
<br>

### 단위 테스트
* Jwt 인증/인가 테스트
* Controller 단위 테스트
* Service 단위 테스트








