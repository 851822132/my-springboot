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
public class StringProducer implements RabbitTemplate.ReturnCallback{
    private static final Logger LOG = LoggerFactory.getLogger(StringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendString(String msg) {
        Date date = new Date();
        String dateString = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(date);
        LOG.info("发送的消息是：{}-{}", dateString, msg);

        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                LOG.info("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                LOG.info("HelloSender 消息发送成功 ");
            }
        });

        // 第一个参数为刚刚定义的队列名称
        rabbitTemplate.convertAndSend(RabbitConfig.STRING, dateString + "-" + msg);
    }


    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        LOG.info("sender return success" + message.toString()+"==="+i+"==="+s+"==="+s1+"==="+s2);
    }
}
