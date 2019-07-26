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
import java.util.Set;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-25 11:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RedisSetTest {
    @Autowired
    RedisUtil redisUtil;

    private final String key = "setNums";

    /**
     * set 对象的特点，不容许重复
     */
    @Test
    public void set(){
        UserEntity userEntity = new UserEntity();
        userEntity.setGuid("guid");
        userEntity.setName("xcxu");
        userEntity.setAge(String.valueOf(20));
        userEntity.setCreateTime(new Date());
        for(int i=0;i<20;i++){
            userEntity.setId(Long.valueOf(i));
            redisUtil.sSet(key,userEntity);
        }
    }

    @Test
    public void get(){
        Set<Object> set = redisUtil.sGet(key);
       set.forEach(s->{
           System.out.println(((UserEntity)s).toString());
       });
    }

    @Test
    public void delete(){
        /**
        *只能删除键值对，而不能删除set里面的元素
        */
        redisUtil.del(key);
    }
}
