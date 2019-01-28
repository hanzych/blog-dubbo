package com.yfancy.common.base.enums;

public enum  KafkaTopicEnum {

    LOG_TOPIC("日志topic"),


    ;





    private String desc;

    KafkaTopicEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
