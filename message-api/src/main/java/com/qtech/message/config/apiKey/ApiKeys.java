package com.qtech.message.config.apiKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/17 09:05:46
 * desc   :
 */


@Component
@ConfigurationProperties(prefix = "api.keys")
public class ApiKeys {

    private Map<String, String> keys = new ConcurrentHashMap<>();

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }

    // 隐藏默认构造器，防止实例化
    private ApiKeys() {}
}
