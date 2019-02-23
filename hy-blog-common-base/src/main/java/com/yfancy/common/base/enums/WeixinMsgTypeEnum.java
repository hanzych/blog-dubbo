package com.yfancy.common.base.enums;

public enum WeixinMsgTypeEnum {

    text("文本消息"),
    image("图片消息"),
    voice("语音消息"),
    video("视频消息"),
    music("音乐消息"),
    news("图文消息"),
    event("事件消息"),

    ;


    private String desc;

    WeixinMsgTypeEnum(String desc) {
        this.desc = desc;
    }

}
