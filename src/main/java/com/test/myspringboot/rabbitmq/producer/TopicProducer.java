package com.test.myspringboot.rabbitmq.producer;

import com.test.myspringboot.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ysh
 * @date: 2019/3/14 16:47
 * @Description:
 */
@Component
public class TopicProducer{
    private static final Logger LOG = LoggerFactory.getLogger(TopicProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String sendString(String msg) {
        Date date = new Date();
        String dateString = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(date);
        LOG.info("发送的消息是：{}-{}", dateString, msg);
        return dateString + msg;
    }

    public void topicTopic1Send(String msg) {
        String dateString = sendString(msg);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey topic.msg，第三个是你要发送的消息
        // 这条信息将会被 topic.a  topic.b接收
        rabbitTemplate.convertAndSend("topicExchange", "topic.msg", dateString);
    }

    public void topicTopic2Send(String msg) {
        String dateString = sendString(msg);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey ，第三个是你要发送的消息
        // 这条信息将会被topic.b接收
        rabbitTemplate.convertAndSend("topicExchange", "topic.good.msg", dateString);
    }

    public void topicTopic3Send(String msg) {
        String dateString = sendString(msg);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey ，第三个是你要发送的消息
        // 这条信息将会被topic.b、topic.c接收
        rabbitTemplate.convertAndSend("topicExchange", "topic.my.z", dateString);
    }

}
