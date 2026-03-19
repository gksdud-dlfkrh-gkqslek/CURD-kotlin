# 장비 예약 시스템 (Equipment Reservation System)

Spring Boot + Kotlin 기반의 장비 예약/반납 관리 REST API 서버입니다.
관리 번호가 있는 장비와 번호 없는 소모성 장비를 구분하여 예약, 반납, 이력 관리를 수행합니다.

## 기술 스택

- **Language**: Kotlin 2.2
- **Framework**: Spring Boot 3.3.5
- **ORM**: Spring Data JPA (Hibernate)
- **Database**: PostgreSQL (운영) / MariaDB (로컬)
- **Security**: Spring Security (세션 기반 인증)
- **Build**: Gradle (Kotlin DSL)
- **Java**: JDK 17
- **배포**: Render (서버) + Supabase (PostgreSQL DB)

## 프로젝트 구조

```
src/main/kotlin/com/bs/crudkotlin/
├── Configuration/
│   ├── PasswordConfig.kt        # BCrypt 비밀번호 인코더
│   └── WebConfig.kt             # CORS 설정, 정적 리소스 핸들러
├── Controller/
│   ├── AuthController.kt        # 인증 API (회원가입, 로그인, 사용자 관리)
│   ├── EquipmentController.kt   # 번호 있는 장비 API
│   └── UnnumberedEquipmentController.kt  # 번호 없는 장비 API
├── DTO/
│   ├── UserDto.kt               # 회원가입/로그인/응답 DTO
│   ├── EquipmentDto.kt          # 장비 DTO
│   ├── UnnumberedEquipmentDto.kt
│   ├── UnnumberedReservationDto.kt
│   └── HistoryDto.kt            # 반납 이력 DTO
├── Entity/
│   ├── UserEntity.kt            # 사용자 (역할, 승인 상태)
│   ├── EquipmentEntity.kt       # 번호 있는 장비
│   ├── UnnumberedEquipmentEntity.kt  # 번호 없는 장비 (재고 관리)
│   ├── UnnumberedReservationEntity.kt # 번호 없는 장비 예약
│   └── HistoryEntity.kt         # 반납 이력
├── Repository/
│   ├── UserRepository.kt
│   ├── EquipmentRepository.kt
│   ├── UnnumberedEquipmentRepository.kt
│   ├── UnnumberedReservationRepository.kt
│   └── HistoryRepository.kt
├── Service/
│   ├── AuthService.kt           # 인증/사용자 비즈니스 로직
│   ├── EquipmentService.kt      # 장비 예약/반납/이력 로직
│   ├── UnnumberedEquipmentService.kt
│   └── HistoryCleanupService.kt # 1년 지난 이력 자동 삭제
└── CrudKotlinApplication.kt
```

## 주요 기능

### 사용자 인증 및 관리

- **회원가입**: 전화번호(010-0000-0000 형식) + 비밀번호 + 이름으로 가입, 가입 후 관리자 승인 필요
- **로그인/로그아웃**: 세션 기반 인증
- **역할 관리**: `USER`(일반 사용자), `ADMIN`(관리자)
- **승인 시스템**: 가입 시 `PENDING` 상태 → 관리자가 `APPROVED`로 승인

### 번호 있는 장비 (Equipment)

- 장비 CRUD (등록 시 파일 첨부 가능)
- 번호(`num`) 또는 이름(`name`)으로 검색
- 예약 / 예약 취소
- 반납 요청 → 관리자 반납 승인 (반납 시 상태와 이력 기록)
- 반납 대기 목록 조회

### 번호 없는 장비 (Unnumbered Equipment)

- 소모성/수량 기반 장비 등록 및 재고 관리
- 예약 시 재고 차감, 취소 시 재고 복구
- 예약 시 파일 첨부 가능
- 반납 요청 → 관리자 승인 흐름 동일

### 반납 이력 (History)

- 반납 승인 시 자동으로 이력 저장 (장비명, 사용자, 기간, 반납 상태 등)
- 관리자: 전체 이력 조회 / 사용자: 본인 이력 조회
- 1년 지난 이력 자동 삭제 (스케줄러)

## API 엔드포인트

### 인증 (`/api/auth`)

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/signup` | 회원가입 |
| POST | `/login` | 로그인 |
| POST | `/logout` | 로그아웃 |
| GET | `/me` | 내 정보 조회 |
| GET | `/users` | 전체 사용자 조회 |
| DELETE | `/user/{id}` | 사용자 삭제 |
| GET | `/pending` | 승인 대기 목록 |
| PUT | `/approve/{id}` | 사용자 승인 |
| PUT | `/reject/{id}` | 사용자 거절 |
| PUT | `/admin/{id}` | 관리자 권한 부여 |

### 번호 있는 장비 (`/api/equipments`)

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/` | 전체 장비 조회 |
| GET | `/num/{num}` | 번호로 조회 |
| GET | `/name/{name}` | 이름으로 조회 |
| POST | `/` | 장비 등록 (multipart/form-data) |
| PUT | `/{id}` | 장비 정보 수정 |
| DELETE | `/{id}` | 장비 삭제 |
| PUT | `/reserve/{id}` | 예약 |
| PUT | `/cancel/{id}` | 예약 취소 |
| PUT | `/return/{id}` | 반납 요청 |
| PUT | `/return/approve/{id}` | 반납 승인 |
| GET | `/return/pending` | 반납 대기 목록 |
| GET | `/history` | 전체 이력 조회 (관리자) |
| GET | `/history/me` | 내 이력 조회 |

### 번호 없는 장비 (`/api/unnumbered`)

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/` | 전체 조회 |
| POST | `/` | 장비 등록 |
| DELETE | `/{id}` | 장비 삭제 |
| PUT | `/stock/{id}` | 정보(재고) 수정 |
| POST | `/reserve/{id}` | 예약 (파일 첨부 가능) |
| PUT | `/cancel/{id}` | 예약 취소 |
| PUT | `/return/{id}` | 반납 요청 |
| PUT | `/return/approve/{id}` | 반납 승인 |
| GET | `/return/pending` | 반납 대기 목록 |

## 시작하기

### 로컬 실행

1. MariaDB에 `blackshark` 데이터베이스 생성

2. 프로젝트 실행:
```bash
./gradlew bootRun
```

기본 포트: `8080`, 프로필: `local`

### Docker 실행

```bash
docker build -t crud-kotlin .
docker run -p 8080:8080 crud-kotlin
```

### 환경 설정

| 프로필 | DB | 설정 파일 |
|--------|-----|-----------|
| `local` | MariaDB (localhost:3306) | `application-local.properties` |
| `prod` | PostgreSQL (환경변수) | `application-prod.properties` |

운영 환경 필요 환경변수:

- `DATABASE_URL` - PostgreSQL 접속 URL
- `DATABASE_USERNAME` - DB 사용자명
- `DATABASE_PASSWORD` - DB 비밀번호
- `PORT` - 서버 포트 (기본 8080)
