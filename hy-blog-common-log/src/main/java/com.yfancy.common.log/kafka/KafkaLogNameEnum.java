package com.yfancy.common.log.kafka;

public enum KafkaLogNameEnum {

    web_timer("定时服务器日志枚举"),
    web_boss("定时服务器日志枚举"),
    service_user("定时服务器日志枚举"),
    service_vip("定时服务器日志枚举"),

    ;


    private String desc;

    KafkaLogNameEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
