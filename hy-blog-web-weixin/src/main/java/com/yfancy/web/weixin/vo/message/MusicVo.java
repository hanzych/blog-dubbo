package com.yfancy.web.weixin.vo.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class MusicVo {

    @XStreamAlias("Title")
    private String title;

    @XStreamAlias("Description")
    private String desc;

    @XStreamAlias("MusicUrl")
    private String musicUrl;

    @XStreamAlias("HQMusicUrl")
    private String hQMusicUrl;

    @XStreamAlias("ThumbMediaId")
    private String thumbMediaId;
}
