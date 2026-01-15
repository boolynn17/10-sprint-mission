package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(String name);
    Channel read(UUID id);
    List<Channel> readAll();
    List<Channel> getChnlsByUser(UUID userId);
    Channel update(UUID id, String name);
    void delete(UUID id);
}
