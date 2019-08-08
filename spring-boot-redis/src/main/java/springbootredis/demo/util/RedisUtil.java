package springbootredis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xuxiangcheng
 * @create: 2019-07-24 16:57
 **/
@Component
public class RedisUtil {

        @Autowired
        private RedisTemplate<String, Object> redisTemplate;

        public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        /**
         * 指定缓存失效时间
         * @param key 键
         * @param time 时间(秒)
         * @return
         */
        public boolean expire(String key,long time){
            try {
                if(time>0){
                    redisTemplate.expire(key, time, TimeUnit.SECONDS);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 根据key 获取过期时间
         * @param key 键 不能为null
         * @return 时间(秒) 返回0代表为永久有效
         */
        public long getExpire(String key){
            return redisTemplate.getExpire(key,TimeUnit.SECONDS);
        }

        /**
         * 判断key是否存在
         * @param key 键
         * @return true 存在 false不存在
         */
        public boolean hasKey(String key){
            try {
                return redisTemplate.hasKey(key);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 删除缓存
         * @param key 可以传一个值 或多个
         */
        @SuppressWarnings("unchecked")
        public void del(String ... key){
            if(key!=null&&key.length>0){
                if(key.length==1){
                    redisTemplate.delete(key[0]);
                }else{
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }
            }
        }

        //============================String=============================
        /**
         * 普通缓存获取
         * @param key 键
         * @return 值
         */
        public Object get(String key){
            return key==null?null:redisTemplate.opsForValue().get(key);
        }

        /**
         * 普通缓存放入
         * @param key 键
         * @param value 值
         * @return true成功 false失败
         */
        public boolean set(String key,Object value) {
            try {
                redisTemplate.opsForValue().set(key, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 普通缓存放入并设置时间
         * @param key 键
         * @param value 值
         * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
         * @return true成功 false 失败
         */
        public boolean set(String key,Object value,long time){
            try {
                if(time>0){
                    redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                }else{
                    set(key, value);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 递增
         * @param key 键
         * @param delta 要增加几(大于0)
         * @return
         */
        public long incr(String key, long delta){
            if(delta<0){
                throw new RuntimeException("递增因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, delta);
        }

        /**
         * 递减
         * @param key 键
         * @param delta 要减少几(小于0)
         * @return
         */
        public long decr(String key, long delta){
            if(delta<0){
                throw new RuntimeException("递减因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, -delta);
        }

        //================================Map=================================
        /**
         * HashGet
         * @param key 键 不能为null
         * @param item 项 不能为null
         * @return 值
         */
        public Object hget(String key,String item){
            return redisTemplate.opsForHash().get(key, item);
        }

        /**
         * 获取hashKey对应的所有键值
         * @param key 键
         * @return 对应的多个键值
         */
        public Map<Object,Object> hmget(String key){
            return redisTemplate.opsForHash().entries(key);
        }

        /**
         * HashSet
         * @param key 键
         * @param map 对应多个键值
         * @return true 成功 false 失败
         */
        public boolean hmset(String key, Map<String,Object> map){
            try {
                redisTemplate.opsForHash().putAll(key, map);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * HashSet 并设置时间
         * @param key 键
         * @param map 对应多个键值
         * @param time 时间(秒)
         * @return true成功 false失败
         */
        public boolean hmset(String key, Map<String,Object> map, long time){
            try {
                redisTemplate.opsForHash().putAll(key, map);
                if(time>0){
                    expire(key, time);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 向一张hash表中放入数据,如果不存在将创建
         * @param key 键
         * @param item 项
         * @param value 值
         * @return true 成功 false失败
         */
        public boolean hset(String key,String item,Object value) {
            try {
                redisTemplate.opsForHash().put(key, item, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 向一张hash表中放入数据,如果不存在将创建
         * @param key 键
         * @param item 项
         * @param value 值
         * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
         * @return true 成功 false失败
         */
        public boolean hset(String key,String item,Object value,long time) {
            try {
                redisTemplate.opsForHash().put(key, item, value);
                if(time>0){
                    expire(key, time);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 删除hash表中的值
         * @param key 键 不能为null
         * @param item 项 可以使多个 不能为null
         */
        public void hdel(String key, Object... item){
            redisTemplate.opsForHash().delete(key,item);
        }

        /**
         * 判断hash表中是否有该项的值
         * @param key 键 不能为null
         * @param item 项 不能为null
         * @return true 存在 false不存在
         */
        public boolean hHasKey(String key, String item){
            return redisTemplate.opsForHash().hasKey(key, item);
        }

        /**
         * hash递增 如果不存在,就会创建一个 并把新增后的值返回
         * @param key 键
         * @param item 项
         * @param by 要增加几(大于0)
         * @return
         */
        public double hincr(String key, String item,double by){
            return redisTemplate.opsForHash().increment(key, item, by);
        }

        /**
         * hash递减
         * @param key 键
         * @param item 项
         * @param by 要减少记(小于0)
         * @return
         */
        public double hdecr(String key, String item,double by){
            return redisTemplate.opsForHash().increment(key, item,-by);
        }

        //============================set=============================
        /**
         * 根据key获取Set中的所有值
         * @param key 键
         * @return
         */
        public Set<Object> sGet(String key){
            try {
                return redisTemplate.opsForSet().members(key);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 根据value从一个set中查询,是否存在
         * @param key 键
         * @param value 值
         * @return true 存在 false不存在
         */
        public boolean sHasKey(String key,Object value){
            try {
                return redisTemplate.opsForSet().isMember(key, value);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 将数据放入set缓存
         * @param key 键
         * @param values 值 可以是多个
         * @return 成功个数
         */
        public long sSet(String key, Object...values) {
            try {
                return redisTemplate.opsForSet().add(key, values);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * 将set数据放入缓存
         * @param key 键
         * @param time 时间(秒)
         * @param values 值 可以是多个
         * @return 成功个数
         */
        public long sSetAndTime(String key,long time,Object...values) {
            try {
                Long count = redisTemplate.opsForSet().add(key, values);
                if(time>0) {
                    expire(key, time);
                }
                return count;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * 获取set缓存的长度
         * @param key 键
         * @return
         */
        public long sGetSetSize(String key){
            try {
                return redisTemplate.opsForSet().size(key);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * 移除值为value的
         * @param key 键
         * @param values 值 可以是多个
         * @return 移除的个数
         */
        public long setRemove(String key, Object ...values) {
            try {
                Long count = redisTemplate.opsForSet().remove(key, values);
                return count;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        //===============================list=================================

        /**
         * 获取list缓存的内容
         * @param key 键
         * @param start 开始
         * @param end 结束  0 到 -1代表所有值
         * @return
         */
        public List<Object> lGet(String key, long start, long end){
            try {
                return redisTemplate.opsForList().range(key, start, end);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获取list缓存的长度
         * @param key 键
         * @return
         */
        public long lGetListSize(String key){
            try {
                return redisTemplate.opsForList().size(key);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * 通过索引 获取list中的值
         * @param key 键
         * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
         * @return
         */
        public Object lGetIndex(String key,long index){
            try {
                return redisTemplate.opsForList().index(key, index);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 将list放入缓存
         * @param key 键
         * @param value 值
         * @return
         */
        public boolean lSet(String key, Object value) {
            try {
                redisTemplate.opsForList().rightPush(key, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 将list放入缓存
         * @param key 键
         * @param value 值
         * @param time 时间(秒)
         * @return
         */
        public boolean lSet(String key, Object value, long time) {
            try {
                redisTemplate.opsForList().rightPush(key, value);
                if (time > 0) {
                    expire(key, time);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 将list放入缓存
         * @param key 键
         * @param value 值
         * @return
         */
        public boolean lSet(String key, List<Object> value) {
            try {
                redisTemplate.opsForList().rightPushAll(key, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 将list放入缓存
         * @param key 键
         * @param value 值
         * @param time 时间(秒)
         * @return
         */
        public boolean lSet(String key, List<Object> value, long time) {
            try {
                redisTemplate.opsForList().rightPushAll(key, value);
                if (time > 0) {
                    expire(key, time);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 根据索引修改list中的某条数据
         * @param key 键
         * @param index 索引
         * @param value 值
         * @return
         */
        public boolean lUpdateIndex(String key, long index,Object value) {
            try {
                redisTemplate.opsForList().set(key, index, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 移除N个值为value
         * @param key 键
         * @param count 移除多少个
         * @param value 值
         * @return 移除的个数
         */
        public long lRemove(String key,long count,Object value) {
            try {
                Long remove = redisTemplate.opsForList().remove(key, count, value);
                return remove;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

    /**
     * 监控数据库键
     * @param key 键
     */
    public void watch(String key){
            try {
                redisTemplate.watch(key);

            }catch (Exception e){
                e.printStackTrace();
            }
    }

    /**
     * 批量监控数据库键
     * @param keys 键
     */
    public void watch(Collection<String> keys){
        try {
            redisTemplate.watch(keys);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 移除对数据库的键监视
     */
    public void unWatch(){
        try {
            redisTemplate.unwatch();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  在键不存在的情况下，才会set已存在，则不会做任何命令
     *  键 key 的值设置为 value ， 并将键 key 的生存时间设置为 seconds 秒钟
     * @param key 键
     * @param value 值
     * @param exptime 时间
     * @return
     */
    public Boolean setnex(final String key, final Serializable value, final long exptime){
        try {
            Boolean b = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                    RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                    Object obj = connection.execute("set", keySerializer.serialize(key),
                            valueSerializer.serialize(value),
                            SafeEncoder.encode("NX"),
                            SafeEncoder.encode("EX"),
                            Protocol.toByteArray(exptime));
                    return obj != null;
                }
            });
            return b;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
    *设置 key对应的值为 string类型的 value。
     * 如果key 已经存在，返回 0。如果不存在，则设置键值，并返回1
     * 改操作是原子操作
    */
    public Boolean setnx(final String key, final Serializable value){
        try {
            Boolean b = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                    RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                    Object obj = connection.execute("set", keySerializer.serialize(key),
                            valueSerializer.serialize(value),
                            SafeEncoder.encode("NX"));
                    return obj != null;
                }
            });
            return b;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
    *设置key 对应的值为 string 类型的 value，并指定此键值对应的有效期。
    */
    public Boolean setex(final String key, final Serializable value, final long exptime){
        try {
            Boolean b = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                    RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                    Object obj = connection.execute("set", keySerializer.serialize(key),
                            valueSerializer.serialize(value),
                            SafeEncoder.encode("EX"),
                            Protocol.toByteArray(exptime));
                    return obj != null;
                }
            });
            return b;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 实现get set方法： 命令用于设置指定 key 的值，并返回 key 的旧值
     * @param key
     * @param value
     * @return
     */
    public String getSet(final String key, final String value) {
        if (redisTemplate != null) {
            byte[] bytes = redisTemplate.execute(new RedisCallback<byte[]>() {
                @Override
                public byte[] doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    byte[] keys = serializer.serialize(key);
                    byte[] values = serializer.serialize(value);
                    return connection.getSet(keys, values);
                }
            });
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }


}

