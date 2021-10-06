package com.example.restfulwebservice.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    // 데이터를 저장할 리스트 생성
    private static List<User> users = new ArrayList<>();

    private static Long userCount = 3L;

    static { // static 초기화 블럭
        users.add(new User(1L, "moon", new Date(), "pass1", "701010-1111111"));
        users.add(new User(2L, "Alice", new Date(), "pass2", "701011-1111111"));
        users.add(new User(3L, "Elena", new Date(), "pass3", "701012-1111111"));
    }

    // 전체 사용자 조회
    public List<User> findAll() {
        return users;
    }

    // 개별 사용자 조회
    public User findOne(Long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    // 유저 저장
    public User save(User user) {
        if(user.getId() == null)
            user.setId(++userCount);

        users.add(user);
        return user;
    }

    // 유저 삭제
    public User deleteById(Long id) {
        /*for (User user : users) {
            if(user.getId() == id)

        }*/

        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

    // 유저 수정
    public User update(Long id, User user) {
        /*// 유저 아이디 조회
        for (User findUser : users) {

            if (findUser.getId() == id) {
                findUser.setName(user.getName());
                return findUser;
            }
        }

        return null;*/

        User findUser = findOne(id);

        if(findUser == null)
            return null;

        findUser.setName(user.getName());

        return findUser;
    }
}
