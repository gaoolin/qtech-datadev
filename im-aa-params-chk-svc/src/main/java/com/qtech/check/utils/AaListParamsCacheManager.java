package com.qtech.check.utils;

import com.qtech.check.pojo.AaListParamsStdTemplate;
import com.qtech.check.pojo.AaListParamsStdTemplateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/02/18 15:03:08
 * desc   :
 */

@Component
public class AaListParamsCacheManager {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsCacheManager.class);
    private static final long CACHE_EXPIRY_TIME = 5 * 60 * 1000; // 5分钟缓存过期时间
    private final Map<String, CacheEntry<AaListParamsStdTemplateInfo>> modelInfoCache = new ConcurrentHashMap<>();
    private final Map<String, CacheEntry<AaListParamsStdTemplate>> modelCache = new ConcurrentHashMap<>();
    @Autowired
    private RedisUtil redisUtil;

    public AaListParamsStdTemplateInfo getModelInfo(String prodType) {
        if (modelInfoCache.containsKey(prodType) && !isExpired(modelInfoCache.get(prodType).timestamp)) {
            return modelInfoCache.get(prodType).value;
        }
        // 批量查询 Redis
        String redisKey = "comparison_model_info:" + prodType;
        AaListParamsStdTemplateInfo modelInfo = redisUtil.getAaListParamsStdModelInfo(redisKey);
        if (modelInfo != null) {
            modelInfoCache.put(prodType, new CacheEntry<>(modelInfo));
        }
        return modelInfo;
    }

    public AaListParamsStdTemplate getModel(String prodType) {
        if (modelCache.containsKey(prodType) && !isExpired(modelCache.get(prodType).timestamp)) {
            return modelCache.get(prodType).value;
        }
        // 批量查询 Redis
        String redisKey = "comparison_model:" + prodType;
        AaListParamsStdTemplate model = redisUtil.getAaListParamsStdModel(redisKey);
        if (model != null) {
            modelCache.put(prodType, new CacheEntry<>(model));
        }
        return model;
    }

    private boolean isExpired(long timestamp) {
        return System.currentTimeMillis() - timestamp > CACHE_EXPIRY_TIME;
    }

    // @Scheduled(fixedRate = 300000) // 每5分钟同步一次
    // public void syncCacheWithRedis() {
    //     List<String> allProdTypes = redisUtil.getAllProdTypes();
    //     for (String prodType : allProdTypes) {
    //         cacheManager.refreshTemplate(prodType);
    //     }
    // }

    private static class CacheEntry<T> {
        final T value;
        final long timestamp;

        CacheEntry(T value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }
}