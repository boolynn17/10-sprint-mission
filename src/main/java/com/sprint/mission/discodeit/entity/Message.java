package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity {
    private final UUID channelId;
    private User sender;
    private String text;

    public Message(Channel channel, User sender, String text) {
        this.channelId = channel.getId();
        this.sender = sender;
        this.text = text;
    }

    // Getter 메소드
    public UUID getChannelId() { return channelId; }
    public User getSender() { return sender; }
    public String getText() { return text; }


    // update 메소드
    public void update(String text) {
        this.text = text;
        recordUpdate();
    }

}
