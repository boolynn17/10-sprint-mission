package com.sprint.mission.discodeit.event.listener;

import com.sprint.mission.discodeit.event.message.ChannelCreatedEvent;
import com.sprint.mission.discodeit.event.message.ChannelDeletedEvent;
import com.sprint.mission.discodeit.event.message.ChannelUpdatedEvent;
import com.sprint.mission.discodeit.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseRequiredEventListener {

  private final SseService sseService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onChannelCreated(ChannelCreatedEvent event) {
    sseService.broadcast("channels.created", event.getData());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onChannelUpdated(ChannelUpdatedEvent event) {
    sseService.broadcast("channels.updated", event.getTo());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onChannelDeleted(ChannelDeletedEvent event) {
    sseService.broadcast("channels.deleted", event.getData());
  }

}
