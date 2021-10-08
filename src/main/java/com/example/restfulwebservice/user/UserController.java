package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        public ResponseEntity<User> createUser(@Valid @RequestBody User user) { // application/json 형태의 요청바디를 User에 매핑해준다.
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
        public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable Long id) {
                User user = userDaoService.findOne(id);

                // 기존엔 user id가 없어도 200으로 응답받음
                // user가 null 일 경우 예외 발생
                if (user == null) {
                        throw new UserNotFoundException(String.format("ID[%s] not found", id));
                }

                // HATEOAS
                EntityModel<User> entityModel = EntityModel.of(user);
                WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
                entityModel.add(linkTo.withRel("all-users"));

                return ResponseEntity.ok(entityModel);
        }

        // 기존 사용자 삭제
        @DeleteMapping("/users/{id}")
        public void deleteUser(@PathVariable Long id) {
                User deleteUser = userDaoService.deleteById(id);

                if(deleteUser == null)
                        throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // 유저 수정
        @PutMapping("/users/{id}")
        public User updateUser(@PathVariable Long id, @RequestBody User user) {
                User updateUser = userDaoService.update(id, user);

                if(updateUser == null)
                        throw new UserNotFoundException(String.format("ID[%s] not found", id));

                return updateUser;
        }
}
