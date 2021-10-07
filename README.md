## Spring Boot를 이용한 RESTful Webservice 개발

> 인프런 강의를 듣고 정리한 내용입니다.



### 사용자 목록 조회 API 구현

- UserController -> 실행시 <br>

Autowiring by type from bean name 'userController' via constructor to bean named 'userDaoService'

<br>

### 응답 코드 제어

```java
URI location = ServletUriComponentsBuilder.fromCurrentRequest()
.path("/{id}")
.buildAndExpand(savedUser.getId())
.toUri();
```

response header 에서 Location으로 생성됨

<br>

### 예외처리 핸들링

- 기존 id값이 없는 경우에도 200으로 응답받은 것을 값이 없을 경우 예외처리 해주기

UserNotFoundException을 통해 예외 처리 -> 500에러와 함께 에러 원인이 클라이언트한테 다 보여짐

- // id 값이 없다는 것은 리소스가 존재 x -> 4XX 로 변경<br>
  @ResponseStatus(HttpStatus.NOT_FOUND) 


- 예외클래스 일반화 시키기

예외 객체를 생성해주고 CustomizedResponseEntityExceptionHandler를 통해 해당 예외 객체를 반환해주기

<br>

### 유효성 체크 Validation (전처리)

spring boot 2.5.x 인 경우
dependency 추가
```xml
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

<br>

###다국어처리

```java
// 국제화를 위한 빈등록
@Bean
public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(Locale.KOREA);
    return localeResolver;
}
```
```yml
spring:
  messages:
    basename: messages # 기본 다국어 파일 이름을 messages로 설정 (resources에 생성)
```

<br>

### 응답 데이터 형식 변환 - XML format
```xml
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

- 디펜던시 추가 후 postman을 통해 Request-Header에 Accept=application/xml 추가

<br>

### 응답 데이터 제어 - Filtering

- 도메인 클래스가 가지고 있는 중요한 데이터 값이 클라이언트에 노출되면 위험 -> 숨기기 위한 방법

도메인클래스 각 필드에 SpringBoot에서 제공하는 `@JsonIgnore` 사용

-> 클래스 블록에 일괄적으로 처리도 가능 `@JsonIgnoreProperties`

---

- 조금 더 개발지향적으로 코드를 작성해보자
```java
// 전체 사용자 목록 조회
@GetMapping("/users")
public MappingJacksonValue retrieveAllUsers(){
        List<User> users=userDaoService.findAll();

        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","password");
        FilterProvider filters=new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
        }
```

<br>

### REST API Version 관리

- URI를 이용한 버전관리
- Request Parameter와 Header를 이용한 버전관리

Request Param을 이용한 방법, 
```java
@GetMapping(value = "/users/{id}", params = "version=1")
```

Header값을 이용한 방법,
```java
@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
```

mine타입을 이용한 방법,
```java
@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
```
header에서 Accept=application/vnd.company.appv1+json 