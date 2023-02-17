package com.newland.redis.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * rlock工具
 * Author: leell
 * Date: 2023/1/27 19:37:47
 */
public class RLockUtil {
    public static RLock getMultiLock(RedissonClient redissonClient, String... keys){
        RLock[] rLocks=new RLock[keys.length];
        for(int i=0;i<keys.length;i++){
            rLocks[i]=redissonClient.getLock(keys[i]);
        }
        return redissonClient.getMultiLock(rLocks);
    }
}
