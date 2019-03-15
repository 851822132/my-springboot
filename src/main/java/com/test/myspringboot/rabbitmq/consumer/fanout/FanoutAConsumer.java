package com.test.myspringboot.rabbitmq.consumer.fanout;

import com.test.myspringboot.rabbitmq.consumer.StringConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: ysh
 * @date: 2019/3/14 17:34
 * @Description:
 */
@Component
@RabbitListener(queues = "fanout.a")
public class FanoutAConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(FanoutAConsumer.class);


    /**
     * 消息消费
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(String msg) {
        LOG.info("FanoutA收到的信息是：{}", msg);
    }
}
