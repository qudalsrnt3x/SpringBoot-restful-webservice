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