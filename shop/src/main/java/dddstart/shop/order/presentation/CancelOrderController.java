package dddstart.shop.order.presentation;

import dddstart.shop.order.application.CancelOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CancelOrderController {

    private final CancelOrderService cancelOrderService;

    public CancelOrderController(CancelOrderService cancelOrderService) {
        this.cancelOrderService = cancelOrderService;
    }

    @GetMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Void> orderDetail(@PathVariable("orderId") Long orderId) {
        cancelOrderService.cancel(orderId);
        return ResponseEntity.ok().build();
    }
}
