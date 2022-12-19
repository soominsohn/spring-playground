package dddstart.shop.order.application;

import dddstart.shop.order.domain.Order;
import dddstart.shop.order.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelOrderService {
    private final OrderRepository orderRepository;

    public CancelOrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void cancel(Long orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 order입니다."));
        order.cancel();
    }
}
