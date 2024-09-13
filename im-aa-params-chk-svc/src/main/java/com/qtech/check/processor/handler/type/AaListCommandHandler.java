package com.qtech.check.processor.handler.type;

import com.qtech.check.processor.handler.QtechBaseHandler;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:44:45
 * desc   : 处理命令列表的抽象类
 */
public abstract class AaListCommandHandler<T> implements QtechBaseHandler<T> {

    /**
     * 处理命令。
     *
     * @param parts     命令的部分
     * @param prefixCmd 前缀命令（可选）
     * @return 处理结果
     */
    public abstract T handle(String[] parts, String prefixCmd);

    /**
     * 处理命令（无前缀命令）。
     *
     * @param parts 命令的部分
     * @return 处理结果
     */
    public T handle(String[] parts) {
        return handle(parts, null);
    }
}