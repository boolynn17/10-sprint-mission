package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChannelMapper {
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserMapper userMapper;

    public ChannelDto toDto(Channel channel) {
        if (channel == null) return null;

        // 최근 메시지 시각
        Instant lastMessageAt = messageRepository.findTopByChannelOrderByCreatedAtDesc(channel)
                .map(Message::getCreatedAt)
                .orElse(Instant.MIN);

        // 참여자 목록
        List<UserDto> participants = readStatusRepository.findAllByChannel(channel).stream()
                .map(status -> userMapper.toDto(status.getUser()))
                .toList();

        return new ChannelDto(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                participants,
                lastMessageAt
        );
    }
}
