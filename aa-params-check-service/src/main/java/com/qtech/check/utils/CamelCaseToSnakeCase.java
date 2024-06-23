package com.qtech.check.utils;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/16 09:20:35
 * desc   :
 */


public class CamelCaseToSnakeCase {
    public static String doConvert(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        char[] charArray = camelCase.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (Character.isUpperCase(c)) {
                if (i > 0 && Character.isLowerCase(charArray[i - 1])) {
                    snakeCase.append('_');
                }
                snakeCase.append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }

        return snakeCase.toString();
    }

/*    public static void main(String[] args) {
        String camelCase = "destroyStart";
        String snakeCase = doConvert(camelCase);
        System.out.println(snakeCase);
    }*/
}
