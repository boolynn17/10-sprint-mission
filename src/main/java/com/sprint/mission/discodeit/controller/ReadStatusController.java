package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@ResponseBody
@RequestMapping("/api/readStatuses")
@Tag(name = "ReadStatus", description = "상태 정보 관리 API")
public class ReadStatusController {

  private final ReadStatusService readStatusService;

  @Operation(summary = "생성", description = "사용자 및 채널 ID와 조회한 시간을 받아 상태 정보를 생성합니다.")
  @PostMapping
  public ResponseEntity<ReadStatus> create(@RequestBody ReadStatusCreateRequest request) {
    List<ReadStatus> userReadStatuses = readStatusService.findAllByUserId(request.userId());

    boolean isDuplicate = userReadStatuses.stream()
        .anyMatch(rs -> rs.getChannelId().equals(request.channelId()));

    if (isDuplicate) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    ReadStatus created = readStatusService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @Operation(summary = "정보 수정", description = "마지막으로 조회한 시간을 받아 상태 정보를 수정합니다.")
  @PatchMapping("/{readStatusId}")
  public ResponseEntity<ReadStatus> update(@PathVariable UUID readStatusId,
      @RequestBody ReadStatusUpdateRequest request) {
    ReadStatus updatedReadStatus = readStatusService.update(readStatusId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(updatedReadStatus);
  }

  @Operation(summary = "다건 조회", description = "사용자 ID를 받아 해당 사용자의 상태 정보를 모두 조회합니다.")
  @GetMapping
  public ResponseEntity<List<ReadStatus>> findAllByUserId(@RequestParam("userId") UUID userId) {
    List<ReadStatus> readStatuses = readStatusService.findAllByUserId(userId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(readStatuses);
  }
}
