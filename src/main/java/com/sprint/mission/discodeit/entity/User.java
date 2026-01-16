package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity{
    private String name;
    private String email;
    private List<Message> messageList = new ArrayList<>();
    private List<Channel> channelList = new ArrayList<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void joinChannel(Channel channel) {
        if(!this.channelList.contains(channel)) {
            this.channelList.add(channel);
            channel.getUserList().add(this);
        }
    }


    // Getter 메소드
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List <Channel> getChannelList() { return channelList;}
    public List <Message> getMessageList() { return messageList;}


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
