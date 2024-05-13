package com.qtech.olp.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/09 09:00:02
 * desc   :
 */

@Getter
@Setter
public class AaListParseListActionIsEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    private String code;
    private String data;

    public AaListParseListActionIsEmptyException(String message) {
        super(message);
        this.message = message;
    }

    public AaListParseListActionIsEmptyException(String message, String code, String data) {
        super(message);
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
