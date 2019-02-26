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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  微信媒体控制类
 */
@RestController
public class WxMediaController {

    @Autowired
    private WeixinHelper weixinHelper;

    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_media_upload, method = RequestMethod.POST)
    public Result weixinUploadMedia(@RequestParam("imageUrl") String imageUrl,
                                    @RequestParam("mediaType") String mediaType,
                                    @RequestParam("loadType") int loadType){
        try {
            String result = weixinHelper.uploadMediaToWeixin(imageUrl, mediaType,loadType);
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
