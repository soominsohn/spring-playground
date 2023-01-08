package angel.rabbitmq.consumer.application;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqConsumer {

    @RabbitListener(queues = "sample.q")
    public void listen(String message) {
        System.out.println("이벤트 들어왔음: " + message);
    }
}
