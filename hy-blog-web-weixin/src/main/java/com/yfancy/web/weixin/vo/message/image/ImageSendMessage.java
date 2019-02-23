package com.yfancy.web.weixin.vo.message.image;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.yfancy.web.weixin.vo.message.BaseMessage;
import lombok.Data;

@Data
@XStreamAlias("xml")
public class ImageSendMessage extends BaseMessage {

    @XStreamAlias("Image")
    private ImageVo image;
}

