package springbootredis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import springbootredis.demo.Entity.UserEntity;
import springbootredis.demo.util.RedisUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-25 11:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RedisListTest {
    @Autowired
    RedisUtil redisUtil;

    private final String key = "list";
    @Test
    public void set(){
        List<Object> list = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setGuid("guid");
        userEntity.setName("xcxu");
        userEntity.setAge(String.valueOf(20));
        userEntity.setCreateTime(new Date());
        for(int i=0;i<20;i++){
            userEntity.setId(Long.valueOf(i));
            list.add(userEntity);
        }
        redisUtil.lSet(key,list);
    }

    @Test
    public void get(){
        List<Object> list = redisUtil.lGet(key,5,15);
        list.forEach(s->{
            System.out.println(((UserEntity)s).toString());
        });
    }
}
