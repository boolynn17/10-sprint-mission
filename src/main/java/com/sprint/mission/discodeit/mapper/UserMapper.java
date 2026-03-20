package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final BinaryContentMapper binaryContentMapper;
    //
    private final UserStatusRepository userStatusRepository;

    public UserDto toDto(User user) {
        if (user == null) return null;

        boolean online = userStatusRepository.findByUser(user)
                .map(status -> status.getLastActiveAt().isAfter(Instant.now().minusSeconds(300)))
                .orElse(false);

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                binaryContentMapper.toDto(user.getProfile()),
                online
        );
    }
}
