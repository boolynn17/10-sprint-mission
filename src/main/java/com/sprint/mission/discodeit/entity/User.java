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
    public User update(String name, String email) {
        if (name != null) {
            this.name = name;
        }
        if (email != null) {
            this.email = email;
        }
        recordUpdate();
        return this;
    }
}
