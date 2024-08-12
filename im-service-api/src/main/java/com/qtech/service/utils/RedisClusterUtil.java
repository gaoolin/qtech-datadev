package com.qtech.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/07 10:57:55
 * desc   :
 */

public class RedisClusterUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisClusterUtil.class);
    private static final JedisCluster jedisCluster;

    static {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("10.170.6.24", 6379));
        jedisClusterNodes.add(new HostAndPort("10.170.6.25", 6379));
        jedisClusterNodes.add(new HostAndPort("10.170.6.26", 6379));
        jedisClusterNodes.add(new HostAndPort("10.170.6.141", 6379));
        jedisClusterNodes.add(new HostAndPort("10.170.6.142", 6379));

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxWaitMillis(-1);

        jedisCluster = new JedisCluster(jedisClusterNodes, 5000, 5000, 6, "im@2024", poolConfig);
    }

    public static boolean set(String key, String value) {
        try {
            jedisCluster.set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("Redis set操作失败", e);
            return false;
        }
    }

        public static boolean setEx(String key, Long exMinutes, String value) {
        try {
            jedisCluster.setex(key, exMinutes * 60, value);
            return true;
        } catch (Exception e) {
            logger.error("Redis set操作失败", e);
            return false;
        }
    }

    public static String get(String key) {
        try {
            return jedisCluster.get(key);
        } catch (Exception e) {
            logger.error("Redis get操作失败", e);
            return null;
        }
    }

    public static boolean hSet(String key, String field, String value) {
        try {
            jedisCluster.hset(key, field, value);
            return true;
        } catch (Exception e) {
            logger.error("Redis hset操作失败", e);
            return false;
        }
    }

    public static String hGet(String key, String field) {
        try {
            return jedisCluster.hget(key, field);
        } catch (Exception e) {
            logger.error("Redis hget操作失败", e);
            return null;
        }
    }

    public static void close() {
        if (jedisCluster != null) {
            try {
                jedisCluster.close();
            } catch (Exception e) {
                logger.error("关闭Redis连接失败", e);
            }
        }
    }
}

