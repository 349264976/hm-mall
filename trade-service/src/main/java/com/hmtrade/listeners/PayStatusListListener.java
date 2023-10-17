package com.hmtrade.listeners;

import com.hmtrade.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayStatusListListener {
    private final IOrderService orderService;
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "mark.order.pay.queue",durable = "true"),
                    exchange = @Exchange(name = "pay.topic",type = ExchangeTypes.TOPIC),
                    key = "pay.success"
            )
    )
    public void listenOrderPay(Long orderId){
        //标记订单状态已支付
        orderService.markOrderPaySuccess(orderId);
    }

}
