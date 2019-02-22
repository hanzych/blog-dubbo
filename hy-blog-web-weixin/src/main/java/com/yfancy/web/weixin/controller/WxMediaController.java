package com.yfancy.web.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.yfancy.common.base.Result;
import com.yfancy.common.base.enums.WeixinMsgTypeEnum;
import com.yfancy.common.base.url.WEB_WEIXIN_URL_Mapping;
import com.yfancy.web.weixin.config.WeixinConfig;
import com.yfancy.web.weixin.helper.WeixinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  微信媒体控制类
 */
@RestController
public class WxMediaController {

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private WeixinHelper weixinHelper;


    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_media_upload, method = RequestMethod.POST)
    public Result weixinUploadMedia(){
        String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550839812529&di=6d097782146c238b62ed4daec632b2c5&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8d5494eef01f3a2963a5db079425bc315d607c8d.jpg";
        try {
            String result = weixinHelper.uploadMediaToWeixin(imageUrl, WeixinMsgTypeEnum.image.name());
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.containsKey("media_id")){
                return Result.SUCCESS(jsonObject.getString("media_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ERROR();
    }


}
