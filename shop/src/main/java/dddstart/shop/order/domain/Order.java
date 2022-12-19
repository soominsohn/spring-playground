package dddstart.shop.order.domain;

import dddstart.shop.common.event.Events;
import dddstart.shop.common.event.OrderCanceledEvent;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    private LocalDateTime orderDate;

    public void cancel() {
        verifyNotYetShipped();
        this.orderState = OrderState.CANCELED;
        Events.raise(new OrderCanceledEvent(id));
    }

    private void verifyNotYetShipped() {
        if (!isNotYetShipped()) {
            throw new IllegalArgumentException("이미 배송되었습니다.");
        }
    }

    public boolean isNotYetShipped() {
        return orderState == OrderState.PAYMENT_WAITING || orderState == OrderState.PREPARING;
    }
}
