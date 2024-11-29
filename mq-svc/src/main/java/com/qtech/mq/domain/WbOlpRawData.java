package com.qtech.mq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/23 13:41:06
 * desc   :
 */

@Data
@ToString
@EqualsAndHashCode
public class WbOlpRawData {
    private Date dt;
    private String simId;
    private String mcId;
    private Integer lineNo;
    private String leadX;
    private String leadY;
    private String padX;
    private String padY;
    private Integer checkPort;
    private Integer piecesIndex;
    private Date loadTime;
}
