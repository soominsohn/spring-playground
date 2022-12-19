package dddstart.shop.integration;

import dddstart.shop.eventstore.api.EventEntry;
import dddstart.shop.eventstore.api.EventStore;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventForwarder {
    private static final int DEFAULT_LIMIT_SIZE = 100;

    private final EventStore eventStore;
    private final OffsetStore offsetStore;
    private final EventSender eventSender;

    @Scheduled(initialDelay = 1000L, fixedDelay = 1000L)
    public void getAndSend() {
        long nextOffset = getNextOffset();
        List<EventEntry> events = eventStore.get(nextOffset, DEFAULT_LIMIT_SIZE);
        if (!events.isEmpty()) {
            int processedCount = sendEvent(events);
            if (processedCount > 0) {
                saveNextOffset(nextOffset + processedCount);
            }
        }
    }

    private long getNextOffset() {
        return offsetStore.get();
    }

    private int sendEvent(List<EventEntry> events) {
        int processedCount = 0;
        try {
            for (EventEntry entry : events) {
                eventSender.send(entry);
                processedCount++;
            }
        } catch (Exception e) {
            throw new IllegalStateException("외부 이벤트 처리 오류");
        }
        return processedCount;
    }

    private void saveNextOffset(long nextOffset) {
        offsetStore.update(nextOffset);
    }
}
