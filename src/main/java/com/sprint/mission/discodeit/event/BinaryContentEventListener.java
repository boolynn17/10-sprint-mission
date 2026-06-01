package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinaryContentEventListener {
    private final BinaryContentStorage binaryContentStorage;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBinaryContentCreated(BinaryContentCreatedEvent event) {
        log.debug("바이너리 데이터 저장 시작: id={}", event.getBinaryContent().getId());
        binaryContentStorage.put(event.getBinaryContent().getId(), event.getBytes());
        log.debug("바이너리 데이터 저장 완료: id={}", event.getBinaryContent().getId());
    }
}
