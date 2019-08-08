package springbootredis.demo.UserTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springbootredis.demo.entity.User;
import springbootredis.demo.service.IUserService;
import springbootredis.demo.util.RedisLockUtil;
import springbootredis.demo.util.RedisUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-30 16:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IUserService iUserService;
    @Autowired
    RedisLockUtil redisLockUtil;

    @Test
    public void setUser(){
        for(int i=0;i<10000;i++){
            User user = new User();
            user.setAge("3");
            user.setName("xcxu");
            user.setPassword("123456");
            user.setCreateTime(new Date());

            iUserService.save(user);
        }
    }

    @Test
    public void setRedis(){
        for(int i=0;i<2000;i++){
            User user = new User();
            user.setAge("3");
            user.setName("xcxu");
            user.setPassword("123456");
            user.setCreateTime(new Date());

            redisUtil.set(String.valueOf(i),user);
        }
    }

    public void drawRedPacket(long userId) {
        String key = "draw.redpacket.userid:" + userId;

        boolean lock = redisLockUtil.lock2(key, 60);
        if (lock) {
            try {
                //领取操作
            } finally {
                //释放锁
                redisLockUtil.unLock2(key);
            }
        } else {
            new RuntimeException("重复领取奖励");
        }
    }

}
