package com.space.rbq.order.service.recv.impl;

import com.space.rbq.order.service.recv.TopicRecv;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TopicRecvImpl implements TopicRecv {
    @Override
    public void topicRecv(String message)throws Exception {
        Thread.sleep(3000);
        System.out.println("TopicRecv :"+message);
        System.out.println("接收异步执行中======"+System.currentTimeMillis());
    }
}
