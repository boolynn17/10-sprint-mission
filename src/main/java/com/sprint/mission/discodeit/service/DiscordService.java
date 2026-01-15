package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface DiscordService {
    //UserService
    List<User> getUsersByChnlOrThrow(UUID channelId);

    // ChannelService
    List<Channel> getChnlsByUserOrThrow(UUID userId);

    // MessageService
    List<Message> getMsgsByUserOrThrow(UUID userId);
    List<Message> getMsgsByChnlOrThrow(UUID channelId);


}
