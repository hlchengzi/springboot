package springbootredis.demo.util;

/**
 * @description: 基于redis作为分布式锁
 * @author: xuxiangcheng
 * @create: 2019-08-02 17:30
 **/
public class RedisLock {

    /**
    *  基于 REDIS 的 SETNX()、EXPIRE() 方法做分布式锁
     *
     *  1、setnx(lockkey, 1) 如果返回 0，则说明占位失败；如果返回 1，则说明占位成功
     *  2、expire() 命令对 lockkey 设置超时时间，为的是避免死锁问题。
     *  3、执行完业务代码后，可以通过 delete 命令删除 key。
     *
    */

    /**
     * 基于 REDIS 的 SETNX()、GET()、GETSET()方法做分布式锁
     *
      * 1, setnx(lockkey, 当前时间+过期超时时间)，如果返回 1，则获取锁成功；如果返回 0 则没有获取到锁，转向 2。
      * 2, get(lockkey) 获取值 oldExpireTime ，并将这个 value 值与当前的系统时间进行比较，如果小于当前系统时间，则认为这个锁已经超时，可以允许别的请求重新获取，转向 3。
      * 3, 计算 newExpireTime = 当前时间+过期超时时间，然后 getset(lockkey, newExpireTime) 会返回当前 lockkey 的值currentExpireTime。
      * 4, 判断 currentExpireTime 与 oldExpireTime 是否相等，如果相等，说明当前 getset 设置成功，获取到了锁。如果不相等，说明这个锁又被别的请求获取走了，那么当前请求可以直接返回失败，或者继续重试。
      * 5, 在获取到锁之后，当前线程可以开始自己的业务处理，当处理完毕后，比较自己的处理时间和对于锁设置的超时时间，如果小于锁设置的超时时间，则直接执行 delete 释放锁；如果大于锁设置的超时时间，则不需要再锁进行处理。
      *
     */
}
