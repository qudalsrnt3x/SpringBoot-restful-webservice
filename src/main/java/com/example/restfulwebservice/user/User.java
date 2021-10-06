package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
public class User {

    private Long id;
    private String name;
    private Date joinDate;
}
