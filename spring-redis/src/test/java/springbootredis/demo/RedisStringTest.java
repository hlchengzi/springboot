package springbootredis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import springbootredis.demo.util.RedisUtil;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-25 11:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RedisStringTest {
    @Autowired
    RedisUtil redisUtil;

    private final String key = "string";

    @Test
    public void set(){
        redisUtil.set(key,new String("string"));
    }
    @Test
    public void get(){
        redisUtil.get(key);
    }
    @Test
    public void delete(){
        redisUtil.del(key);
    }
}
