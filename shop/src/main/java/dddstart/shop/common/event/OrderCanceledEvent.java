package dddstart.shop.common.event;

public class OrderCanceledEvent extends Event {

    private Long orderId;

    public OrderCanceledEvent(Long orderId) {
        super();
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
