package dddstart.shop.common.event;

import dddstart.shop.eventstore.api.EventStore;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventStoreHandler {
    private final EventStore eventStore;

    @EventListener(Event.class)
    public void handle(Event event) {
        eventStore.save(event);
    }
}
