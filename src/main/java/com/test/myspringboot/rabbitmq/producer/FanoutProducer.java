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
 * @Description: 广播模式
 */
@Component
public class FanoutProducer{
    private static final Logger LOG = LoggerFactory.getLogger(FanoutProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendFanout(String msg) {
        Date date = new Date();
        String dateString = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(date);
        LOG.info("发送的消息是：{}-{}", dateString, msg);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey 我们不用管空着就可以，第三个是你要发送的消息
        rabbitTemplate.convertAndSend("fanoutExchange", "", dateString + msg);
    }

}
