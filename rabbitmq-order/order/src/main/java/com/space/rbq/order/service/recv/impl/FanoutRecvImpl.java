package com.space.rbq.order.service.recv.impl;

import com.space.rbq.order.service.recv.FanoutRecv;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FanoutRecvImpl implements FanoutRecv {
    @Override

    public void fanoutRecv(String message)throws Exception {
        Thread.sleep(3000);
        System.out.println("FanoutRecv :"+message);
        System.out.println("接收异步执行中======"+System.currentTimeMillis());
    }
}
