package com.yfancy.web.weixin.vo.message;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class EventMessage extends BaseMessage {

    @XStreamAlias("Event")
    private String event;

    @XStreamAlias("EventKey")
    private String eventKey;

    @XStreamAlias("Status")
    private String status;

    @XStreamAlias("MsgID")
    private String msgId;


}
