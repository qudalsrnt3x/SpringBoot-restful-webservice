## Spring Boot를 이용한 RESTful Webservice 개발

> 인프런 강의를 듣고 정리한 내용입니다.



### 사용자 목록 조회 API 구현

- UserController -> 실행시 <br>

Autowiring by type from bean name 'userController' via constructor to bean named 'userDaoService'

### 응답 코드 제어

URI location = ServletUriComponentsBuilder.fromCurrentRequest()<br>
.path("/{id}")<br>
.buildAndExpand(savedUser.getId())<br>
.toUri();

response header 에서 Location으로 생성됨

### 예외처리 핸들링

- 기존 id값이 없는 경우에도 200으로 응답받은 것을 값이 없을 경우 예외처리 해주기

UserNotFoundException을 통해 예외 처리 -> 500에러와 함께 에러 원인이 클라이언트한테 다 보여짐

- // id 값이 없다는 것은 리소스가 존재 x -> 4XX 로 변경<br>
  @ResponseStatus(HttpStatus.NOT_FOUND) 


- 예외클래스 일반화 시키기

예외 객체를 생성해주고 CustomizedResponseEntityExceptionHandler를 통해 해당 예외 객체를 반환해주기

### 유효성 체크 Validation (전처리)

spring boot 2.5.x 인 경우
dependency 추가
```
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>2.5.5</version>
</dependency>
```

- domain에 컬럼에 유효성 검사 어노테이션 붙여준다. 

예외처리를 위해 CustomizedResponseEntityExceptionHandler에서 handleMethodArgumentNotValid
오버라이드 해준다.