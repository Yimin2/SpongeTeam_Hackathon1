# 내일모배우지

## 프로젝트 소개

모든 국비지원 내일배움카드에 대한 개인 리뷰 & 정보 사이트

- **프로젝트 이름**: 내일모배우지
- **프로젝트 주제**: HRD-NET 내일배움카드 리뷰 사이트
- **프로젝트 기간**: 2024. 07. 01. ~ 2024. 08. 31.
- **설명**: Spring Boot 기반의 국비지원 교육과정 리뷰 플랫폼입니다.

## 기술 스택

### Backend
- Java 17
- Spring Boot 3.2.1
- Spring Security
- Spring Data JPA
- OAuth2 Client

### Database
- MySQL 8.0
- H2 Database (개발용)
- Redis

### 인증/인가
- JWT Token (JJWT 0.12.3)
- OAuth2 소셜 로그인

### Cloud & Storage
- AWS S3

### 문서화
- Swagger/OpenAPI 3.0 (SpringDoc)

### View
- Thymeleaf

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/ll/hackathon1team/
│   │   ├── domain/
│   │   │   ├── review/          # 리뷰 도메인
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   └── user/            # 사용자 도메인
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── global/              # 공통 설정 및 유틸리티
│   │       ├── config/          # 설정 클래스
│   │       ├── jpa/             # JPA 공통
│   │       └── security/        # 보안 관련
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       ├── application-test.yml
│       └── templates/
└── test/
```

## 시작하기

### 사전 요구사항

- JDK 17 이상
- MySQL 8.0
- Redis
- Gradle

### 환경 설정

1. `src/main/resources/application-secret.yml` 파일 생성
   ```bash
   cp src/main/resources/application-secret.yml.default src/main/resources/application-secret.yml
   ```

2. `application-secret.yml` 파일에 필요한 설정 입력:
   - 데이터베이스 연결 정보
   - JWT 시크릿 키
   - OAuth2 클라이언트 정보 (Google, Kakao 등)
   - AWS S3 자격 증명

### 실행 방법

#### 개발 환경
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

#### 프로덕션 환경
```bash
./gradlew clean build
java -jar build/libs/Hackathon-1Team-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

#### Docker 사용
```bash
docker build -t hackathon-1team .
docker run -p 8080:8080 hackathon-1team
```

### 테스트

```bash
./gradlew test
```

## API 문서

애플리케이션 실행 후 다음 주소에서 API 문서를 확인할 수 있습니다:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## 주요 기능

- **회원 관리**
  - 로그인/로그아웃
  - 회원가입
  - OAuth2 소셜 로그인 (Google, Kakao 등)
  - JWT 기반 인증/인가

- **리뷰 관리**
  - 리뷰 작성, 조회, 수정, 삭제 (CRUD)
  - 리뷰 검색 및 정렬
  - 리뷰 필터링
  - 페이징 처리

- **파일 관리**
  - 이미지 업로드 (AWS S3)
  - 파일 첨부 기능

- **API 문서화**
  - Swagger/OpenAPI 3.0 자동 문서화

## 브랜치 전략

- `main`: 프로덕션 브랜치
- `dev`: 개발 브랜치
- `feature/*`: 기능 개발 브랜치
