package com.yfancy.web.weixin.vo.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public abstract class BaseMessage {


    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private long createTime;

    @XStreamAlias("MsgType")
    private String msgType;
}
