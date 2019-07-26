package springbootredis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import springbootredis.demo.Entity.UserEntity;
import springbootredis.demo.util.RedisUtil;

import java.util.Date;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-25 11:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RedisHashTest {

    @Autowired
    RedisUtil redisUtil;

    private final String key = "hash";

    /**
    *添加 如果有的话，那么修改，没有的话，那就添加
    */
    @Test
    public void set(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(1));
        userEntity.setGuid("guid");
        userEntity.setName("xcxu");
        userEntity.setAge(String.valueOf(20));
        userEntity.setCreateTime(new Date());
        for(int i=0;i<20;i++) {
            Boolean result = redisUtil.hset(key, String.valueOf(i), userEntity);
        }
    }
    @Test
    public void delete(){
        redisUtil.del(key);
        for(int i=10;i<20;i++) {
            redisUtil.hdel(key, String.valueOf(i));
        }
    }
    @Test
    public void get(){
        UserEntity userEntity = (UserEntity) redisUtil.hget(key,String.valueOf(1));
        System.out.println(userEntity.toString());
    }

}
