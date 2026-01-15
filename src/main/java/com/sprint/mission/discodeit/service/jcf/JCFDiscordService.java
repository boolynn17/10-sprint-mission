package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.DiscordService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

public class JCFDiscordService implements DiscordService {
    private final UserService userService;
    private final ChannelService channelService;
    private final MessageService messageService;

    public JCFDiscordService(UserService userService, ChannelService channelService, MessageService messageService) {
        this.userService = userService;
        this.channelService = channelService;
        this.messageService = messageService;
    }

    @Override
    public List<User> getUsersByChnlOrThrow(UUID channelId) {
        channelService.read(channelId);
        return userService.getUsersByChnl(channelId);
    }

    @Override
    public List<Channel> getChnlsByUserOrThrow(UUID userId) {
        userService.read(userId);
        return channelService.getChnlsByUser(userId);
    }

    @Override
    public List<Message> getMsgsByUserOrThrow(UUID userId) {
        userService.read(userId);
        return messageService.getMsgsByUser(userId);
    }

    @Override
    public List<Message> getMsgsByChnlOrThrow(UUID channelId) {
        channelService.read(channelId);
        return messageService.getMsgsByChnl(channelId);
    }
}
