package dddstart.shop.integration;

import dddstart.shop.eventstore.api.EventEntry;

public interface EventSender {
    void send(EventEntry event);
}
