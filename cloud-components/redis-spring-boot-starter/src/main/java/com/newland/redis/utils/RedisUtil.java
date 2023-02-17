package com.newland.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SETNX_EXPIRE_SCRIPT = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then\n"
            + "return redis.call('expire', KEYS[1], ARGV[2]);\n" + "end\n" + "return 0;";


    /**
     * 通过Key值删除
     * @param keys
     * @return
     */
    public long del(final String... keys) {
        long result=(long) redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }

        });
        return result;
    }


    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @param expireTime 单位:秒
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */

    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存,将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     * @param key
     * @return
     */
    public Object getSet(final String key,Object value) {
        Object result = null;
        ValueOperations operations = redisTemplate.opsForValue();
        result = operations.getAndSet(key, value);
        return result;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public <T> T get(final String key) {
        ValueOperations operations = redisTemplate.opsForValue();
        return (T) operations.get(key);
    }

    /**
     * 批量删除对应的value
     * @param keys
     */

    public void remove(final String... keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 批量删除key
     * @param pattern
     */

    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     * @param key
     */

    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 缓存List
     * @param key
     * @param value
     * @return
     */
    public boolean setListLeft(final String key, Object value) {
        boolean result = false;
        try {
            ListOperations operations = redisTemplate.opsForList();
            operations.leftPush(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 缓存List
     * @param key
     * @param value
     * @return
     */
    public boolean setListRight(final String key, Object value) {
        boolean result = false;
        try {
            ListOperations operations = redisTemplate.opsForList();
            operations.rightPush(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object getListLeft(final String key) {
        Object result = null;
        try {
            ListOperations operations = redisTemplate.opsForList();
            result = operations.leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object getListRight(final String key) {
        Object result = null;
        try {
            ListOperations operations = redisTemplate.opsForList();
            result = operations.rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 缓存Hash
     * @param key
     * @param hashKay
     * @param value
     * @return
     */
    public boolean setHash(final String key, String hashKay,Object value) {
        boolean result = false;
        try {
            HashOperations operations = redisTemplate.opsForHash();
            operations.put(key, hashKay, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 缓存Hash
     * @param key
     * @param value
     * @return
     */
    public boolean setHash(final String key, Map<Object,Object> value) {
        boolean result = false;
        try {
            HashOperations operations = redisTemplate.opsForHash();
            operations.putAll(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存hash
     * @param key
     * @return
     */
    public Object getHash(final String key) {
        Object result = null;
        try {
            HashOperations operations = redisTemplate.opsForHash();
            result = operations.entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @param hashKey
     * @return
     */

    public boolean existsHash(final String key,final String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 删除缓存
     * @param key
     * @param hashKeys
     */
    public void deleteHash(final String key,final Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 设置key并设置有效期
     * @param key
     * @param value
     * @param seconds
     * @return
     */

    public boolean setnxAndExpire(String key,Object value,int seconds){
        RedisScript<Long> script = new DefaultRedisScript<Long>(SETNX_EXPIRE_SCRIPT, Long.class);
        Long result = (Long)redisTemplate.execute(script,Collections.singletonList(key),value,seconds);
        return result>0?true:false;
    }

    /**
     * 设置setNX
     * @param key
     * @param timeout	过期时间（秒）
     * @return
     */
    public boolean setNX(String key, int timeout) {
        Boolean notExists = redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), new byte[0]);
        Boolean expire = redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return notExists != null?notExists.booleanValue():false;
    }

    /**
     * 按指定的值递增或递减
     * @param key
     * @param delta
     * @return
     */
    public Long incr(String key,long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 按指定的值递增或递减
     * @param key
     * @param delta
     * @return
     */
    public Double incr(String key,double delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 返回键值有效期
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }
    /**
     * 更新key过期时间
     * @param key 关键字
     * @param timeout 有效期
     * @param unit 时间单位
     * @return
     */
    public Boolean expire(String key,long timeout,TimeUnit unit){
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置key并设置有效期
     * @param script
     * @param keys
     * @param value
     * @param seconds
     * @return
     */
    public Number execute(RedisScript<Number> script, List<String> keys, Object value, int seconds){
        Number no = (Number) redisTemplate.execute(script,keys,value,seconds);
        return no;
    }
}
