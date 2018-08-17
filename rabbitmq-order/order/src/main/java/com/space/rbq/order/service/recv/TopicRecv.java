package com.space.rbq.order.service.recv;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.messages")
public interface TopicRecv {
/*
* topic模式
* */
@RabbitHandler
@Async
    public void topicRecv(String message)throws Exception;

}
