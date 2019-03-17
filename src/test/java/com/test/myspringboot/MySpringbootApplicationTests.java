package com.test.myspringboot;

import com.test.myspringboot.rabbitmq.producer.StringProducer;
import com.test.myspringboot.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySpringbootApplicationTests {

    @Autowired
    private StringProducer stringProducer;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTransactionTemplate;

    @Test
    public void contextLoads() {
        stringProducer.sendString("张三");
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testRedis() {
//        redisUtil.set("张三1","张三1");
//        redisUtil.setTranscation("张三2","张三2");
//        redisUtil.setTransaction("张三3","张三3",60000);
//        int a = 1/0;


        redisTransactionTemplate.opsForValue().set("foo", "bar");

// read operation executed on a free (not tx-aware)
        redisTransactionTemplate.keys("*");

// returns null as values set within transaction are not visible
        String hehe = (String) redisTransactionTemplate.opsForValue().get("foo");
        System.out.println("取到的结果是：" + hehe);
//        throw new RuntimeException("test");
    }

    @Test
//    @Transactional(rollbackFor = Exception.class)
    public void testRedis1() {
        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                redisTemplate.opsForValue().set("张三", "张三");
//                int a = 1 / 0;
                return redisOperations.exec();
//                throw new RuntimeException("test");
            }
        };
        redisTemplate.execute(sessionCallback);
    }

    @Test
    @Transactional
    public void testError() {
        redisTransactionTemplate.opsForValue().set("d", "aa");
//        redisTransactionTemplate.opsForValue().set(null, "cc");//抛出异常
        redisTransactionTemplate.opsForValue().set("f", "cc");
    }
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testError1() {
//        redisTemplate.opsForValue().set("d", "aa");
//        redisTemplate.opsForValue().set(null, "cc");//抛出异常
//        redisTemplate.opsForValue().set("f", "cc");
        System.out.println("-------------------" + redisTransactionTemplate.opsForValue().get("张三"));
        redisTransactionTemplate.opsForValue().set("aaaaa", "cc");
//        System.out.println(redisUtil.setTranscation("aaa", "cc"));
    }
}
