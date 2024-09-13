package com.qtech.service.utils.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;

import static com.qtech.service.common.Constants.EQ_REVERSE_CTRL_INFO_RESPONSE_MSG_LENGTH;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/14 10:32:45
 * desc   :  反控机台信息显示内容
 */

public class IoTReverseMsgBuilder {
    private static final String PREFIX_WB_OLP = "ECheck";
    private static final String PREFIX_AA_LIST = "Parameter Monitoring AA-List";
    private static final String NG_MSG = " NG";
    private static final String SEPARATOR = ": ";

    /**
     * 构建响应消息。
     *
     * @param info EqReverseCtrlInfo对象
     * @return 合成的消息
     */
    public static String buildResponseMessage(EqReverseCtrlInfo info) {
        // 获取description字段的内容，并限制长度
        String description = truncateDescription(info.getDescription());

        // 获取格式化的时间戳
        String formattedTime = info.getFormattedChkDt();

        if (info.getCode() == 0) {
            if ("wb-olp".equals(info.getSource())) {
                return PREFIX_WB_OLP + SEPARATOR + formattedTime + " " + description;
            } else if ("aa-list".equals(info.getSource())) {
                return PREFIX_AA_LIST + SEPARATOR + formattedTime + " " + description;
            } else {
                return "unknown check source.";
            }
        } else {
            if ("wb-olp".equals(info.getSource())) {
                return PREFIX_WB_OLP + NG_MSG + SEPARATOR + formattedTime + " " + description;
            } else if ("aa-list".equals(info.getSource())) {
                return PREFIX_AA_LIST + NG_MSG + SEPARATOR + formattedTime + " " + description;
            } else {
                return "unknown check source.";
            }
        }
    }

    /**
     * 限制description字段的内容长度。
     *
     * @param description 原始描述
     * @return 限制长度后的描述
     */
    private static String truncateDescription(String description) {
        if (description == null) {
            return "";
        }
        return description.length() > EQ_REVERSE_CTRL_INFO_RESPONSE_MSG_LENGTH ? description.substring(0, EQ_REVERSE_CTRL_INFO_RESPONSE_MSG_LENGTH) + "..." : description;
    }
}
