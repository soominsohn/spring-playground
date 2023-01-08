package angel.rabbitmq.producer.presentation;

import angel.rabbitmq.producer.application.RabbitmqProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProducerController {

    private final RabbitmqProducer rabbitmqProducer;

    @PostMapping ("/messages")
    public ResponseEntity<Void> send() {
        rabbitmqProducer.sendHello("rabbit");
        return ResponseEntity.ok().build();
    }
}
