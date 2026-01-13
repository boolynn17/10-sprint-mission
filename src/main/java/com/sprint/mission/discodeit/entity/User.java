package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User extends BaseEntity{
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter 메소드
    public String getName() { return name; }
    public String getEmail() { return email; }


    // update 메소드
    public void update(String name, String email) {
        this.name = name;
        this.email = email;
        recordUpdate();
    }
    public void updateName(String name) {
        this.name = name;
        recordUpdate();
    }
    public void updateEmail(String email) { // 질문: 이렇게 여러 개로 나누는 게 좋을지, 하나의 메소드로 사용하는 것이 좋을지?
        this.email = email;
        recordUpdate();
    }

}
