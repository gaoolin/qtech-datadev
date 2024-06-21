package com.qtech.rabbitmq.common.dynamic;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/08 10:11:08
 * desc   :
 */



/**
 * 数据源名称枚举
 */
public enum DataSourceNames {
    DORIS_K8S("doris-k8s"), POSTGRESQL("postgresql");

    private final String name;

    DataSourceNames(String name) {
        this.name = name;
    }
}

