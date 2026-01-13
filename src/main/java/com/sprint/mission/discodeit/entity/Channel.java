package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Channel extends BaseEntity {
    private String name;

    public Channel(String name) {
        this.name = name;
    }

    // Getter 메소드
    public String getName() { return name; }

    // update 메소드
    public Channel update(String name) {
        this.name = name;
        recordUpdate();
        return this;
    }

}
