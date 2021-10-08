package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;


    // User : Post -> 1 : (0~N), Main : Sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 방식
    @JsonIgnore // 외부에 데이터 노출 x
    private User user;
}
