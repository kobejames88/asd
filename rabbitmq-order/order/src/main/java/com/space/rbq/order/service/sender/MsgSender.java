package com.space.rbq.order.service.sender;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
* author ozx
* */
@Component
public interface MsgSender {
   @Async
    public void DirectSend(String uuid, Object message)throws Exception;

   @Async
    public void TopicSend(String uuid, Object message)throws Exception;
    @Async
    public void FanoutSend(String uuid, Object message)throws Exception;
}
