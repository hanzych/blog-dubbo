package com.yfancy.web.weixin.vo.message;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("item")
public class ArticlesVo {

    @XStreamAlias("Title")
    private String title;

    @XStreamAlias("Description")
    private String description;

    @XStreamAlias("PicUrl")
    private String picUrl;

    @XStreamAlias("Url")
    private String url;
}
