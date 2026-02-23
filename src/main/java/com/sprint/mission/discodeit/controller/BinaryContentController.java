package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@ResponseBody
@RequestMapping("/api/binaryContents")
@Tag(name = "BinaryContent", description = "첨부 파일 데이터 관련 API")
public class BinaryContentController {

  private final BinaryContentService binaryContentService;

  @Operation(summary = "조회", description = "첨부 파일의 ID를 받아 파일의 상세 정보 및 데이터를 조회합니다.")
  @GetMapping("/{binaryContentId}")
  public ResponseEntity<BinaryContent> find(@PathVariable UUID binaryContentId) {
    BinaryContent binaryContent = binaryContentService.find(binaryContentId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(binaryContent);
  }

  @Operation(summary = "다건 조회", description = "여러 개의 첨부 파일 ID 목록을 받아 한 번에 여러 파일 정보를 조회합니다.")
  @GetMapping
  public ResponseEntity<List<BinaryContent>> findAllByIdIn(
      @RequestParam("binaryContentIds") List<UUID> binaryContentIds) {
    List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(binaryContentIds);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(binaryContents);
  }
}
