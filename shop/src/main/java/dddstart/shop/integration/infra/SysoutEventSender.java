package dddstart.shop.integration.infra;

import dddstart.shop.eventstore.api.EventEntry;
import dddstart.shop.integration.EventSender;
import org.springframework.stereotype.Component;

@Component
public class SysoutEventSender implements EventSender {

    @Override
    public void send(EventEntry event) {
        System.out.println("EventSender send event: "+ event);
    }
}
