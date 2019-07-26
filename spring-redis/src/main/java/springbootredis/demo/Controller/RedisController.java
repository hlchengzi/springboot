package springbootredis.demo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootredis.demo.Entity.UserEntity;
import springbootredis.demo.util.RedisUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-24 17:02
 **/
@Slf4j
@Controller
@RequestMapping("/redis")
public class RedisController {
    /**
     * ExpireTime: redis中存储的过期时间60s
     */
    private static int ExpireTime = 60;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @Description: http://localhost:8088/redis/set?key=1&guid=123456
     * @Author: xuxiangcheng
     * @Date: 2019/7/24
     */
    @GetMapping("/set")
    @ResponseBody
    public boolean redisSet(String key, String guid) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(1));
        userEntity.setGuid(guid);
        userEntity.setName("xcxu");
        userEntity.setAge(String.valueOf(20));
        userEntity.setCreateTime(new Date());

        //return redisUtil.set(key,userEntity,ExpireTime);

        return redisUtil.set(key, userEntity);
    }

    /**
     * @Description: 根据键值获取相关信息
     * http://localhost:8088/redis/get
     * @Date: 2019/7/24
     */
    @RequestMapping("/get")
    @ResponseBody
    public Object redisGet(String key) {
        return redisUtil.get(key);
    }

    /**
     * 指定缓存失效时间为固定的60秒，当固定的时间过后
     */
    @GetMapping("/expire")
    @ResponseBody
    public boolean expire(String key) {
        return redisUtil.expire(key, ExpireTime);
    }

    /**
     * 向一张hash表中放入数据
     *
     */
    @GetMapping("/hashSet")
    @ResponseBody
    public boolean hashSet(String key, String item) {

        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(Long.valueOf(1));
            userEntity.setGuid("guid");
            userEntity.setName("xcxu");
            userEntity.setAge(String.valueOf(20));
            userEntity.setCreateTime(new Date());
            return redisUtil.hset(key, item, userEntity);
        } catch (Exception e) {
            return false;
        }
    }

}
