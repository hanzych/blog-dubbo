package com.yfancy.web.weixin.vo.menu;

import lombok.Data;

import java.util.List;

@Data
public class MenuButtonVo {

    private String type;

    private String name;

    private String key;

    private List<SubButtonVo> sub_button;


    public MenuButtonVo(String type, String name, String key, List<SubButtonVo> sub_button) {
        this.type = type;
        this.name = name;
        this.key = key;
        this.sub_button = sub_button;
    }
}
