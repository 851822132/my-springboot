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

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ysh
 * @date: 2019/3/14 16:47
 * @Description:
 */
@Component
public class StringProducer implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {
    private static final Logger LOG = LoggerFactory.getLogger(StringProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    public void sendString(String msg) {
        Date date = new Date();
        String dateString = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(date);
        LOG.info("发送的消息是：{}-{}", dateString, msg);

//        rabbitTemplate.setReturnCallback(this);
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                LOG.info("HelloSender消息发送失败" + cause + correlationData.toString());
//            } else {
//                LOG.info("HelloSender 消息发送成功 ");
//            }
//        });

        // 第一个参数为刚刚定义的队列名称
        rabbitTemplate.convertAndSend(RabbitConfig.STRING, dateString + "-" + msg);
    }

    /**
     * ReturnCallback接口用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调。
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不会回调,消息就丢了)
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        LOG.info("sender return success" + message.toString() + "===" + replyCode + "===" + replyText + "===" + exchange + "===" + routingKey);
    }

    /**
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调。即是否发送到RabbitMQ交换器，由RabbitMQ回调
     * 如果消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            //业务逻辑

            LOG.info("HelloSender消息发送失败" + cause + correlationData.toString());
        } else {
            LOG.info("HelloSender 消息发送成功 ");
        }
    }
}
