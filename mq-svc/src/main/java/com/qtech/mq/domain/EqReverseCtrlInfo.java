package com.qtech.mq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 08:30:15
 * desc   :
 */
@Data
@EqualsAndHashCode
@ToString
public class EqReverseCtrlInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String simId;
    private String source;
    private String prodType;
    private String chkDt;
    private Integer code;
    private String description;
    transient private int version;
}
