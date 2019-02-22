package com.yfancy.common.base.enums;


/**
 * 微信菜单按钮类型
 */
public enum  WeixinButtonTypeEnum {

    click("点击"),
    view("搜索"),
    miniprogram("小程序"),
    scancode_waitmsg("扫码提示"),
    scancode_push("扫码推送"),
    pic_sysphoto("系统拍照"),
    pic_photo_or_album("系统拍照或者相册发图"),
    pic_weixin("微信相册发图"),
    location_select("发送位置"),
    media_id("图片"),
    view_limited("图文消息"),


    ;


    private String desc;

    WeixinButtonTypeEnum(String desc) {
        this.desc = desc;
    }
}
