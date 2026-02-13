# 📅 ScheduleProject (일정관리 앱 만들기 Upgrade!)
Lv6까지만 구현된 프로젝트입니다.

## 주요 기능

### 📅 일정 관리 (Schedule CRUD)

- 일정 생성

- 일정 전체 조회 / 단건 조회

- 일정 수정

- 일정 삭제
---

### 👤 사용자 관리 (User CRUD)

- 회원가입

- 로그인 

- 로그아웃 처리

    - 로그아웃 요청 시 서버 세션을 invalidate 처리하여
인증 상태를 즉시 해제하도록 구현

- 유저 전체 조회 / 단건 조회

- 유저 정보 수정

- 회원 탈퇴

※ 별도의 인가 계층을 분리하지 않고
User 도메인 내부에 인증 로직을 통합하여 구현

---

### 🔐 인증 & 세션 기반 로그인

- 세션/쿠키 기반 인증 방식 적용

- 로그인 성공 시 서버 세션 생성

- 로그인하지 않은 사용자는 일정 관리 API 접근 제한

---

### 🛡️ 보안 및 Validation

- 회원가입 및 로그인 시 비밀번호 정책 적용
     - 회원가입 시 email, userName 중복 체크

- 영문 + 숫자 + 특수문자 포함 최소 8자 이상 제한

- BCryptPasswordEncoder를 활용한 단방향 해싱 적용

- 평문 비밀번호 저장 방지

- Salt 기반 해시로 보안 강화

---

### ⚙️ 전역 예외 처리 (Global Exception Handling)

- @ControllerAdvice 기반 전역 예외 처리 구조 적용

- MethodArgumentNotValidException 등 Validation 예외 중앙 관리

- 비즈니스 예외와 검증 예외를 분리하여 처리

### ✔️ 개선 효과

- HTTP Status Code 표준화

- API 응답 일관성 향상

- 컨트롤러 로직 단순화 및 유지보수성 개선

---

### Postman API 명세서 (유저, 일정, 로그인API까지만 구현)

https://documenter.getpostman.com/view/51131938/2sBXcBn2Tf

---

**ERD 다이어그램**
<img width="578" height="242" alt="image" src="https://github.com/user-attachments/assets/d8aadd79-37a7-4c11-9025-a2c84b28d60c" />


