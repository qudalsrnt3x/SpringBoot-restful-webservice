package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
public class User {

    private Long id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;

    @Past
    private Date joinDate;
}
