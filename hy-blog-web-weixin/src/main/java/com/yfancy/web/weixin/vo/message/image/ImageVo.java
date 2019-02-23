package com.yfancy.web.weixin.vo.message.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;


@Data
public class ImageVo {

    @XStreamAlias("MediaId")
    private String mediaId;

    public ImageVo(String mediaId) {
        this.mediaId = mediaId;
    }
}
