package com.test.myspringboot.rabbitmq.consumer;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @author: ysh
 * @date: 2019/3/14 16:56
 * @Description:
 */
@Component
@RabbitListener(queues="string")
public class StringConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(StringConsumer.class);


    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(String msg, Channel channel, Message message) {
        LOG.info("收到的string消息是：{}",msg);
        try {
            //处理业务逻辑
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            LOG.info("处理string消息成功");
        } catch (Exception e) {
            LOG.error("处理string消息异常",e);
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            LOG.info("处理string消息失败");
        }

    }
}
