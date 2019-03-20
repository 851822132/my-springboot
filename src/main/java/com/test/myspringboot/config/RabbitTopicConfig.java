package com.test.myspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ysh
 * @date: 2019/3/14 16:41
 * @Description: 广播模式，只要跟它绑定的队列都会通知并且接受到消息
 * topicA的key为topic.msg 那么他只会接收包含topic.msg的消息
 * topicB的key为topic.#那么他只会接收topic开头的消息
 * topicC的key为topic.*.Z那么他只会接收topic.B.z这样格式的消息
 */
@Configuration
public class RabbitTopicConfig {


    @Bean
    public Queue dlxQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-message-ttl 声明了消息过期时间
//        params.put("x-message-ttl", 60 * 1000);
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", "topicExchange");
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", "topic.msg");
        return new Queue("dlxQueue", true, false, false, params);
    }

    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("dlxExchange");
    }

    @Bean
    public Binding bindingDlxExchange() {
//        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("all");
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("all");
    }


    @Bean
    public Queue topiocA() {
        return new Queue("topic.a", true);
    }

    @Bean
    public Queue topicB() {
        return new Queue("topic.b", true);
    }

    @Bean
    public Queue topicC() {
        return new Queue("topic.c");
    }

    /**
     * 定义个topic交换器
     *
     * @return
     */
    @Bean
    TopicExchange topicExchange() {
        // 定义一个名为fanoutExchange的fanout交换器
        return new TopicExchange("topicExchange");
    }

    /**
     * 将定义的topicA队列与topicExchange交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithA() {
        return BindingBuilder.bind(topiocA()).to(topicExchange()).with("topic.msg");
    }

    /**
     * 将定义的topicB队列与topicExchange交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithB() {
        return BindingBuilder.bind(topicB()).to(topicExchange()).with("topic.#");
    }

    /**
     * 将定义的topicC队列与topicExchange交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithC() {
        return BindingBuilder.bind(topicC()).to(topicExchange()).with("topic.*.z");
    }
}