package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        public void createUser(@RequestBody User user) { // application/json 형태의 요청바디를 User에 매핑해준다.
                User savedUser = userDaoService.save(user);
        }

        // 개별 사용자 조회
        // GET /users/1 or /users/10
        @GetMapping("/users/{id}")
        public User retrieveUser(@PathVariable Long id) {
                return userDaoService.findOne(id);
        }

}
