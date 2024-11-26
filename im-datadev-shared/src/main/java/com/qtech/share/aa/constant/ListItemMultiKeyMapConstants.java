package com.qtech.share.aa.constant;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 10:37:49
 * desc   :
 */

/**
 * A utility class for mapping multiple keys to a single value using a predefined mapping.
 */
@Component
public class ListItemMultiKeyMapConstants {
    private final Map<String, String> valueMap = new HashMap<>();

    // Static mapping for key-value relationships
    public static final Map<String, Set<String>> KEY_MAP;

    static {
        Map<String, Set<String>> keyMap = new HashMap<>();
        keyMap.put("Aa", new HashSet<>(Arrays.asList("AA1", "AA2", "AA3")));
        keyMap.put("MtfCheck", new HashSet<>(Arrays.asList("MTF_Check", "MTF_Check1", "MTF_Check2")));
        keyMap.put("ChartAlignment", new HashSet<>(Arrays.asList("ChartAlignment1", "ChartAlignment2")));
        keyMap.put("SaveMtf", new HashSet<>(Collections.singletonList("Save_MTF")));

        KEY_MAP = Collections.unmodifiableMap(keyMap); // Make it immutable
    }

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
}