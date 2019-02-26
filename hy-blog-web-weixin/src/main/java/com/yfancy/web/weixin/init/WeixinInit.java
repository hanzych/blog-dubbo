package com.yfancy.web.weixin.init;

import com.yfancy.common.base.init.Weixin_Init_Param;
import com.yfancy.web.weixin.config.WeixinConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信初始化信息存放类
 */
@Component
@Slf4j
public class WeixinInit {

    @Autowired
    private WeixinConfig weixinConfig;

    /**
     * 初始化微信文本信息回复信息
     */
    public void initWeixinTextRespParam(){
        log.info("[WeixinInit][initWeixinTextRespParam]初始化微信文本信息回复信息list，开始");
        String init_text_resp_param = weixinConfig.getInit_text_resp_param();
        String[] params = init_text_resp_param.split(",");
        List<String> list = new ArrayList<>();
        for (String param : params){
            list.add(param);
        }
        Weixin_Init_Param.textRespParamMsg = list;
        log.info("[WeixinInit][initWeixinTextRespParam]初始化微信文本信息回复信息list，结束");
    }


    /**
     * 初始化微信到模版id和我们自定义的类型
     */
    public void initWeixinTempleteIdWithType(){
        log.info("[WeixinInit][initWeixinTempleteIdWithType]初始化微信模版templateID，开始");
        Weixin_Init_Param.templeteMap.put(100, "EnpxoqnWE47oBxguCkpIEQvA_U9MF0YOwuV5fDjsvBI");
        log.info("[WeixinInit][initWeixinTempleteIdWithType]初始化微信模版templateID，结束");
    }

    /**
     * 初始化微信media
     */
    public void initWeixinMediaMap(){
        log.info("[WeixinInit][initWeixinMediaMap]初始化微信media，开始");
        List<String> media_image_list = new ArrayList<>();
        media_image_list.add("XQozvnyVD2TZL37wDyfCoxb2POYt7NFxfWRhS0KU7M8");
        media_image_list.add("XQozvnyVD2TZL37wDyfCo1zN9jrA152fZrc0lCk4IsE");
        media_image_list.add("XQozvnyVD2TZL37wDyfCo3IxRRZ2fSN1g7ipp3Dx258");
        List<String> media_thumb_list = new ArrayList<>();
        media_thumb_list.add("QozvnyVD2TZL37wDyfCo3u-GOmL7GrGurOj0aRjTU8");
        media_thumb_list.add("XQozvnyVD2TZL37wDyfCowmnlf4-LjWXq41CR1pyu0o");
        Weixin_Init_Param.mediaMap.put("image",media_image_list);
        Weixin_Init_Param.mediaMap.put("thumb",media_thumb_list);
        log.info("[WeixinInit][initWeixinTempleteIdWithType]初始化微信media，结束");
    }

}
