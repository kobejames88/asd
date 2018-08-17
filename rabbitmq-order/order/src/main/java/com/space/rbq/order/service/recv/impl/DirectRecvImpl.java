package com.space.rbq.order.service.recv.impl;

import com.space.rbq.order.service.recv.DirectRecv;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* author ozx
* */
@Component
public class DirectRecvImpl implements DirectRecv {
    @Override
    public void directRecv(String message)throws  Exception {
        // 处理消息
        Thread.sleep(3000);
        System.out.println("单点消息接收 :"+message);
        System.out.println("接收异步执行中======"+System.currentTimeMillis());


    }





}
