package com.qtech.check.constant;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 10:37:49
 * desc   :
 */

@Component
public class ListItemMultiKeyMapConstants {
    private final Map<String, String> valueMap = new HashMap<>();

    // 使用静态方法初始化映射
    static final Map<String, Set<String>> KEY_MAP = createKeyMap();

    private static Map<String, Set<String>> createKeyMap() {
        Map<String, Set<String>> keyMap = new HashMap<>();
        keyMap.put("AA", new HashSet<>(Arrays.asList("AA1", "AA2", "AA3")));
        keyMap.put("Init", new HashSet<>(Arrays.asList("Init", "ReInit")));
        // 可以继续添加其他映射
        return keyMap;
    }

    // 构造函数中使用常量初始化 valueMap
    public ListItemMultiKeyMapConstants() {
        for (Map.Entry<String, Set<String>> entry : KEY_MAP.entrySet()) {
            addMapping(entry.getValue(), entry.getKey());
        }
    }

    private void addMapping(Set<String> keys, String value) {
        for (String key : keys) {
            valueMap.put(key, value);
        }
    }

    public String get(String key) {
        return valueMap.get(key);
    }

    public static void main(String[] args) {
        ListItemMultiKeyMapConstants multiKeyMap = new ListItemMultiKeyMapConstants();

        System.out.println(multiKeyMap.get("AA1")); // 输出: value1
        System.out.println(multiKeyMap.get("AA2")); // 输出: value1
        System.out.println(multiKeyMap.get("AA3")); // 输出: value1
        System.out.println(multiKeyMap.get("key4")); // 输出: null
        System.out.println(multiKeyMap.get(null)); // 输出: null
    }
}
