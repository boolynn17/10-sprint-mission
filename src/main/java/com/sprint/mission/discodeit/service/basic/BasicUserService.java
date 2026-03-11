package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User create(UserCreateRequest userCreateRequest,
                       Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {
        String username = userCreateRequest.username();
        String email = userCreateRequest.email();
        // 중복 체크 로직
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with username " + username + " already exists");
        }

        // 프로필 객체 생성
        BinaryContent profile = optionalProfileCreateRequest
                .map(req -> new BinaryContent(req.fileName(), (long) req.bytes().length, req.contentType(), req.bytes()))
                .orElse(null);

        // UserStatus 객체 생성
        UserStatus status = new UserStatus();
        status.update(Instant.now());

        User user = new User(
                username,
                email,
                userCreateRequest.password(),
                profile,
                status
        );

        status.setUser(user);
        return userRepository.save(user);
    }

    @Override
    public UserDto find(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public User update(UUID userId, UserUpdateRequest userUpdateRequest,
                       Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

        String newUsername = userUpdateRequest.newUsername();
        String newEmail = userUpdateRequest.newEmail();

        // 중복 체크 로직
        if (!user.getEmail().equals(newEmail)) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("User with email " + newEmail + " already exists");
            }
        }
        if (!user.getUsername().equals(newUsername)) {
            if (userRepository.existsByUsername(newUsername)) {
                throw new IllegalArgumentException("User with username " + newUsername + " already exists");
            }
        }

        // 프로필 객체 생성
        BinaryContent newProfile = optionalProfileCreateRequest
                .map(req -> new BinaryContent(req.fileName(), (long) req.bytes().length, req.contentType(), req.bytes()))
                .orElse(null);

        user.update(
                userUpdateRequest.newUsername(),
                userUpdateRequest.newEmail(),
                userUpdateRequest.newPassword(),
                newProfile,
                user.getStatus()
        );

        return user;
    }

    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

        userRepository.delete(user);
    }
}
