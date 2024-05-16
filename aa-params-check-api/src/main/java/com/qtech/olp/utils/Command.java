package com.qtech.olp.utils;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/15 15:49:51
 * desc   :
 */


public class Command {
    int num;
    String command;
    String subsystem;
    String enable;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public Command(int num, String command, String subsystem, String enable) {
        this.num = num;
        this.command = command;
        this.subsystem = subsystem;
        this.enable = enable;
    }

    // Getters and setters if needed
}