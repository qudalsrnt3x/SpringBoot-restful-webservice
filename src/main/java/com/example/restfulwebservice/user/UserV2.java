package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
@NoArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User{

    private String grade;   // 등급

}
