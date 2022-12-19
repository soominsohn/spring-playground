package dddstart.shop.eventstore.ui;

import dddstart.shop.eventstore.api.EventEntry;
import dddstart.shop.eventstore.api.EventStore;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EventApi {

    private final EventStore eventStore;

    @GetMapping("/api/events")
    public List<EventEntry> list(@RequestParam("offset") Long offset, @RequestParam("limit") Long limit) {
        return eventStore.get(offset, limit);
    }
}
