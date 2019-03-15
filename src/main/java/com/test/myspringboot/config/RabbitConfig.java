package com.test.myspringboot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ysh
 * @date: 2019/3/14 16:41
 * @Description:
 */
@Configuration
public class RabbitConfig {

    /**
     * 定义队列名
     */
    public final static String STRING = "string";

    /**
     * 定义string队列
     *
     * @return
     */
    @Bean
    public Queue string() {
        return new Queue(STRING);
    }
}