package com.yfancy.web.weixin.vo.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class TextMessage extends BaseMessage{

    @XStreamAlias("Content")
    private String content;

    @XStreamAlias("MsgId")
    private String msgId;

}
