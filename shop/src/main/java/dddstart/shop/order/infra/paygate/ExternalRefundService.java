package dddstart.shop.order.infra.paygate;

import dddstart.shop.order.application.RefundService;
import org.springframework.stereotype.Component;

@Component
public class ExternalRefundService implements RefundService {
    @Override
    public void refund(Long orderId) {
        System.out.printf("refund order [%s]\n", orderId);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("외부 서비스 실행로직 종료");
    }
}
