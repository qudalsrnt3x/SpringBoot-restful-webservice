package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자
//@JsonIgnoreProperties(value = {"password", "ssn"})
@NoArgsConstructor
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;

    @Past
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
    private Date joinDate;

//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요.")
    private String password;

//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요.")
    private String ssn;

    @OneToMany(mappedBy = "user") // user테이블의 데이터와 매핑
    private List<Post> posts;

    // Post 컬럼이 없는 생성자 생성
    public User(Long id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
