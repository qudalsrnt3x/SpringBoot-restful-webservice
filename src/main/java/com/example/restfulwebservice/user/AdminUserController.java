package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

        private final UserDaoService userDaoService;

        // 전체 사용자 목록 조회
        @GetMapping("/users")
        public MappingJacksonValue retrieveAllUsers() {
                List<User> users = userDaoService.findAll();

                SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password");
                FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
                mappingJacksonValue.setFilters(filters);

                return mappingJacksonValue;
        }

        // 개별 사용자 조회
        // GET /users/1 or /users/10
        @GetMapping("/v1/users/{id}")
        public MappingJacksonValue retrieveUserV1(@PathVariable Long id) {
                User user = userDaoService.findOne(id);

                // 기존엔 user id가 없어도 200으로 응답받음
                // user가 null 일 경우 예외 발생
                if (user == null) {
                        throw new UserNotFoundException(String.format("ID[%s] not found", id));
                }

                // 포함할 필터 등록
                SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "password", "ssn");

                // 사용가능한 필터로 만들어주기
                FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
                mappingJacksonValue.setFilters(filters);

                return mappingJacksonValue;
        }

        // 개별 사용자 조회
        // GET /users/1 or /users/10
        @GetMapping("/v2/users/{id}")
        public MappingJacksonValue retrieveUserV2(@PathVariable Long id) {
                User user = userDaoService.findOne(id);

                // 기존엔 user id가 없어도 200으로 응답받음
                // user가 null 일 경우 예외 발생
                if (user == null) {
                        throw new UserNotFoundException(String.format("ID[%s] not found", id));
                }

                // User -> User2 copy
                UserV2 userV2 = new UserV2();
                BeanUtils.copyProperties(user, userV2); // id, name, joinDate, password, ssn
                userV2.setGrade("VIP");


                // 포함할 필터 등록
                SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");

                // 사용가능한 필터로 만들어주기
                FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
                mappingJacksonValue.setFilters(filters);

                return mappingJacksonValue;
        }
}
