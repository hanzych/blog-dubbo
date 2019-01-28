package com.yfancy.common.base.enums;

public enum  KafkaRoleEnum {

    LOG_ROLE("日志角色")
    ;


    private String desc;

    KafkaRoleEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
