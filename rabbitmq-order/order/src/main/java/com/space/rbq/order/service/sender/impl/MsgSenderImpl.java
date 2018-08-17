package com.space.rbq.order.service.sender.impl;


import com.space.rbq.order.config.RabbitMqConfig;
import com.space.rbq.order.service.sender.MsgSender;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
* author ozx
* */
@Component
public class MsgSenderImpl implements MsgSender {
//异步定时发送接受rabbitmq定时信息
@Autowired
private RabbitTemplate rabbitTemplate;
    @Override
    public void DirectSend(String uuid, Object message)throws Exception {
        CorrelationData correlationData=new CorrelationData(uuid);
        rabbitTemplate.convertAndSend("hello",
                message, correlationData);
        Thread.sleep(3000);
        System.out.println("发送消息:"+message);
        System.out.println("发送异步执行中======"+System.currentTimeMillis());
    }

    @Override
    public void TopicSend(String uuid, Object message)throws Exception {
            String msg="Topic发送消息:"+message;
            System.out.println(msg);
            CorrelationData correlationData=new CorrelationData(uuid);
            this.rabbitTemplate.convertAndSend("exchange","topic.messages",msg,correlationData);
            System.out.println("Topic发送消息 UUID : "+correlationData.getId());
            System.out.println("发送异步执行中======"+System.currentTimeMillis());


    }

    @Override
    public void FanoutSend(String uuid, Object message)throws Exception {
        CorrelationData correlationData = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend("fanoutExchange", "queue_one_key2",
                message, correlationData);
        Thread.sleep(3000);
        System.out.println("发送消息:"+message);
        System.out.println("发送异步执行中======"+System.currentTimeMillis());

    }
}
