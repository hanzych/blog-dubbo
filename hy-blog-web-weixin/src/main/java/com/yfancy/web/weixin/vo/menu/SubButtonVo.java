package com.yfancy.web.weixin.vo.menu;

import lombok.Data;

import java.util.List;

@Data
public class SubButtonVo {

    private String type;

    private String name;

    private String key;

    private String url;

    private String appid;

    private String pagepath;

    private List<SubButtonVo> subButton;

    public SubButtonVo(String type, String name, String key, String url, String appid, String pagepath, List<SubButtonVo> subButton) {
        this.type = type;
        this.name = name;
        this.key = key;
        this.url = url;
        this.appid = appid;
        this.pagepath = pagepath;
        this.subButton = subButton;
    }
}
