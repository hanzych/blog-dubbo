package com.yfancy.common.base.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 微信初始化信息存放类
 */
public class Weixin_Init_Param {

    /**
     * 微信文本信息回复信息list
     */
    public static List<String> textRespParamMsg = new ArrayList<>();

    /**
     * 微信模版id和对应的类型的初始化map
     */
    public static HashMap<Integer, String> templeteMap = new HashMap<>();


    /**
     * 微信媒体map
     */
    public static HashMap<String, List<String>> mediaMap = new HashMap<>();
}
