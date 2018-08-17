package com.space.rbq.order.service.recv;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")

public interface DirectRecv{
    @RabbitHandler
    @Async
    public void directRecv(String message)throws  Exception;
}
