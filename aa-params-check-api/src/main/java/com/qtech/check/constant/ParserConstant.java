package com.qtech.check.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/15 10:44:51
 * desc   :
 */


public class ParserConstant {
    public static final Set<String> ABBREVIATIONS = new HashSet<>(Arrays.asList("SID", "MTF", "UVON", "UVOFF", "VCM", "OIS")); // 添加缩写列表
    public static final Set<String> IGNORE_SPECIAL_CHARS = new HashSet<>(Arrays.asList("#", " ")); // 忽略字符列表
    public static final Set<String> IGNORE_LIST_ITEMS = new HashSet<>(Arrays.asList("AA1", "AA2", "AA3")); // 忽略列表项
}
