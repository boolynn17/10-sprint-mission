package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class Channel extends BaseEntity {
    private String name;
    private List<User> userList = new ArrayList<>();
    private List<Message> messageList = new ArrayList<>();

    public Channel(String name) {
        this.name = name;
    }

    // Getter 메소드
    public String getName() { return name; }
    public List<User> getUserList() {
        return userList;
    }
    public List<Message> getMessageList() {
        return messageList;
    }


    // update 메소드
    public Channel update(String name) {
        this.name = name;
        recordUpdate();
        return this;
    }

}
