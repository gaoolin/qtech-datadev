package com.qtech.pulsar.pojo;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 14:05:20
 * desc   :  TODO
 */

public class MessageDto {

    private static final long serialVersionUID = 1L;

    int id;

    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
