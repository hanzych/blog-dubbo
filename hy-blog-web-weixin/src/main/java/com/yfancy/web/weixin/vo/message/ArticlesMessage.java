package com.yfancy.web.weixin.vo.message;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("xml")
public class ArticlesMessage extends BaseMessage {

    @XStreamAlias("ArticleCount")
    private int articleCount;

    @XStreamAlias("Articles")
    private List<ArticlesVo> articles;
}
