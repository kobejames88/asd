package com.space.rbq.order.service.recv;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"fanout.A","fanout.B","fanout.C"}, containerFactory = "rabbitListenerContainerFactory")
public interface FanoutRecv {
    /*
    * fanout发送
    * */
    @RabbitHandler
    @Async
    public void fanoutRecv(String message)throws Exception;

}
