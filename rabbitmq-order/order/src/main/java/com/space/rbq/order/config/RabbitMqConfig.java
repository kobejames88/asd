package com.space.rbq.order.config;

import com.space.rbq.order.mqcallback.MsgSendConfirmCallBack;
import com.space.rbq.order.mqcallback.MsgSendReturnCallback;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq配置
 * @author zhuzhe
 * @date 2018/5/25 13:37
 * @email 1529949535@qq.com
 */
@Configuration
public class RabbitMqConfig {

    /**
     * key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，
     * 消息将会转发给queue参数指定的消息队列
     */
    /** 队列key1*/
    public static final String ROUTING_KEY_1 = "queue_one_key1";
    public static final String ROUTING_KEY_2 = "queue_one_key2";

    @Autowired
    private QueueConfig queueConfig;

    @Autowired
    private ExchangeConfig exchangeConfig;

    @Autowired
    private ConnectionFactory connectionFactory;


    /**
     * 将消息队列1和交换机1进行绑定,指定队列key1
     */
    @Bean
    public Binding binding_one() {
        return BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.directExchange()).with(RabbitMqConfig.ROUTING_KEY_1);
    }

    /**
     * 将消息队列2和交换机1进行绑定,指定队列key2
     */
    @Bean
    public Binding binding_two() {
        return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.directExchange()).with(RabbitMqConfig.ROUTING_KEY_2);
    }

    /**
     * 定义rabbit template用于数据的接收和发送
     * 可以设置消息确认机制和回调
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // template.setMessageConverter(); 可以自定义消息转换器  默认使用的JDK的，所以消息对象需要实现Serializable
        // template.setMessageConverter(new Jackson2JsonMessageConverter());

        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());

        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        template.setReturnCallback(msgSendReturnCallback());
        template.setMandatory(true);
        return template;
    }

 /*   @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }*/
    /**
     * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
     * 1.如果消息没有到exchange,则confirm回调,ack=false
     * 2.如果消息到达exchange,则confirm回调,ack=true
     * 3.exchange到queue成功,则不回调return
     * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
     */

    /**
     * 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
     * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
     * @return
     */
    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }
    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    /*
     *
     * TopicExchange 将路由键和某模式进行匹配。
     * 此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，
     * 符号“*”匹配一个词。
     * 因此“audit.#”能够匹配到“audit.irs.corporate”
     * 但是“audit.*” 只会匹配到“audit.irs”
     */


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }
    //===============以上是验证topic Exchange的队列==========


    //===============以下是验证Fanout Exchange的队列==========
    @Bean
    public Queue aMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue bMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue cMessage() {
        return new Queue("fanout.C");
    }

    /*
     *
     * FanoutExchange 类似广播信息
     * 会将消息转发到与其绑定的队列上
     */


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }
    //===============以上是验证Fanout Exchange的队列==========


    /*

     *
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param queueMessage
     * @param exchange
     * @return
     */


    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /*
     *
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessages
     * @param exchange
     * @return
     */


    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    /*
     *
     * aMessage 队列绑定
     * @param aMessage
     * @param fanoutExchange
     * @return
     */


    @Bean
    Binding bindingExchangeA(Queue aMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(aMessage).to(fanoutExchange);
    }

    /*
     *
     * aMessage 队列绑定
     * @param bMessage
     * @param fanoutExchange
     * @return
     */


    @Bean
    Binding bindingExchangeB(Queue bMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(bMessage).to(fanoutExchange);
    }

    /**
     * aMessage 队列绑定
     * @param cMessage
     * @param fanoutExchange
     * @return*/


    @Bean
    Binding bindingExchangeC(Queue cMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cMessage).to(fanoutExchange);
    }

    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

    @Bean
    public MsgSendReturnCallback msgSendReturnCallback(){
        return new MsgSendReturnCallback();
    }

}
