package com.space.rbq.order.controller;

import com.space.rbq.order.service.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuzhe
 * @date 2018/5/25 16:00
 * @email 1529949535@qq.com
 */
@RestController
public class SendController {
    @Autowired
    private MsgSender msgSender;

  /*  @RequestMapping("/FanoutSend")
    public String msgSender(){
        for(int i=0;i<10;i++){
            msgSender.FanoutSend();
        }
        return "OK";
    }

    @RequestMapping("/DirectSend")
    public String DirectSend(String msg){
        for(int i=0;i<10;i++){
            msgSender.DirectSend(msg);
        }
        return "OK";
    }

    @RequestMapping("/TopicSend")
    public String TopicSend(){
        for(int i=0;i<10;i++){
            msgSender.TopicSend();
        }
        return "OK";
    }*/
    @RequestMapping("/MsgSender")
  public String DirectSender()throws Exception{
        for(int i=0;i<10;i++) {
            msgSender.DirectSend("123123123", "agasgasd");
        }
        return "Ok";
    }
  /*  @RequestMapping("/MsgRecv")
    public String MsgRecv(String message){
       msgRecv.directRecv(message);
        return "Ok";
    }*/
    @RequestMapping("/TopSender")
    public String topSender()throws Exception{
        for(int i=0;i<5;i++) {
            msgSender.TopicSend("123123123", "agasgasd");
        }
        return "Ok";
    }
    @RequestMapping("/FanoutSender")
    public String FanoutSender()throws Exception{
        for(int i=0;i<5;i++) {
            msgSender.FanoutSend("qd12", "12312");
        }
        return "Ok";
    }




}
