package com.test.myspringboot;

import com.test.myspringboot.rabbitmq.producer.StringProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySpringbootApplicationTests {

    @Autowired
    private StringProducer stringProducer;

    @Test
    public void contextLoads() {
        stringProducer.sendString("张三");
    }

}
