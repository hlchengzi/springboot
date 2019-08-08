package springbootredis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-08-02 17:34
 **/
@Component
public class RedisLockUtil {
    private static final int defaultExpire = 60;

    @Autowired
    RedisUtil redisUtil;

    private RedisLockUtil() {
        //
    }

    /**
     * 加锁
     *
     * @param key    redis key
     * @param expire 过期时间，单位秒
     * @return true:加锁成功，false，加锁失败
     */
    public boolean lock(String key, int expire) {

        Boolean status = redisUtil.setnx(key, "1");
        // status = redisService.setnx(key, "1");

        if (status) {
            redisUtil.expire(key, expire);
            return true;
        }

        return false;
    }

    public  boolean lock(String key) {
        return lock2(key, defaultExpire);
    }

    /**
     * 加锁
     *
     * @param key    redis key
     * @param expire 过期时间，单位秒
     * @return true:加锁成功，false，加锁失败
     */
    public  boolean lock2(String key, int expire) {


        long value = System.currentTimeMillis() + expire;
        Boolean status = redisUtil.setnx(key, String.valueOf(value));
        /**
         * 存在才为0，不存在表示1，1，表示取锁成功
         * 0，表示已经有锁，则取锁不成功
         */
        if (status) {
            return true;
        }

        long oldExpireTime = (Long) redisUtil.get(key);
        if (oldExpireTime < System.currentTimeMillis()) {
            //超时
            long newExpireTime = System.currentTimeMillis() + expire;
            long currentExpireTime = Long.parseLong(redisUtil.getSet(key, String.valueOf(newExpireTime)));
            if (currentExpireTime == oldExpireTime) {
                return true;
            }
        }
        return false;
    }

    public void unLock1(String key) {
        redisUtil.del(key);
    }

    public void unLock2(String key) {
        long oldExpireTime = (Long) redisUtil.get(key);
        if (oldExpireTime > System.currentTimeMillis()) {
            redisUtil.del(key);
        }
    }

}
