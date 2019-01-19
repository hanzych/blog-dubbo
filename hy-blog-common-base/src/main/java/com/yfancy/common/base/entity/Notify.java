package com.yfancy.common.base.entity;

import com.yfancy.common.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class Notify extends BaseEntity {


    private Date createTime;

    /** 最后一次通知时间 **/
    private Date lastNotifyTime;

    /** 通知次数 **/
    private Integer notifyTimes = 0;

    /** 限制通知次数 **/
    private Integer limitNotifyTimes = 5;

    /** 通知URL **/
    private String url;

    /** 商户编号 **/
    private String merchantNo;

    /** 商户订单号 **/
    private String merchantOrderNo;

    /** 通知类型 NotifyTypeEnum **/
    private String notifyType;
}
