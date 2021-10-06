package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

        private final UserDaoService userDaoService;

        // 전체 사용자 목록 조회
        @GetMapping("/users")
        public List<User> retrieveAllUsers() {
                return userDaoService.findAll();
        }

        // 유저 등록
        @PostMapping("/users")
        public ResponseEntity<User> createUser(@RequestBody User user) { // application/json 형태의 요청바디를 User에 매핑해준다.
                User savedUser = userDaoService.save(user);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();

                // response header에 Location: http://localhost:8088/users/4 으로 확인 가능

                return ResponseEntity.created(location).build();
        }

        // 개별 사용자 조회
        // GET /users/1 or /users/10
        @GetMapping("/users/{id}")
        public User retrieveUser(@PathVariable Long id) {
                User user = userDaoService.findOne(id);

                // 기존엔 user id가 없어도 200으로 응답받음
                // user가 null 일 경우 예외 발생
                if (user == null) {
                        throw new UserNotFoundException(String.format("ID[%s] not found", id));
                }

                return user;
        }

}
