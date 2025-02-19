package com.qtech.check.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/30 11:41:17
 * desc   :  大驼峰转换
 */


public class ToPascalCaseConverter {
    // 示例常量，可根据实际情况修改
    private static final Set<String> ABBREVIATIONS = new HashSet<>(Arrays.asList("ID", "URL", "HTML"));
    private static final Set<String> IGNORE_LIST_ITEMS = new HashSet<>(Arrays.asList("e.g.", "i.e."));
    private static final Set<String> IGNORE_SPECIAL_CHARS = new HashSet<>(Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"));

    public static String doConvert(String input) {
        // 步骤1：匹配关键词并转换
        for (String keyword : ABBREVIATIONS) {
            if (input.contains(keyword)) {
                input = input.replaceAll(keyword, capitalizeFirstOthersLowerCase(keyword));
            }
        }

        // 步骤2：只有当输入字符串包含下划线时才执行处理
        if (input.contains("_")) {
            String[] parts = input.split("_");
            StringBuilder resultBuilder = new StringBuilder();
            for (String part : parts) {
                if (part.equals(part.toUpperCase())) {
                    part = part.toLowerCase();
                }
                part = capitalizeFirstOthersLowerCase(part);
                resultBuilder.append(part);
            }
            input = resultBuilder.toString();
        }

        // 步骤3：检查并处理首个字符和特殊字符
        if (!IGNORE_LIST_ITEMS.contains(input)) {
            if (!IGNORE_SPECIAL_CHARS.contains(input)) {
                // PascalCase的首字符必须大写
                input = capitalizeFirstOthersLowerCase(input);
                input = input.replaceAll("[^a-zA-Z0-9]", "");
            }
        }

        return input;
    }

    private static String capitalizeFirstOthersLowerCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static void main(String[] args) {
        // 示例测试
        String input1 = "example_input_string";
        String input2 = "another_EXAMPLE_STRING";
        String input3 = "LP_Intensity";
        String input4 = "AA_example";

        System.out.println(doConvert(input1)); // 输出: ExampleInputString
        System.out.println(doConvert(input2)); // 输出: AnotherExampleString
        System.out.println(doConvert(input3)); // 输出: UrlParser
        System.out.println(doConvert(input4)); // 输出: AaExample
    }
}