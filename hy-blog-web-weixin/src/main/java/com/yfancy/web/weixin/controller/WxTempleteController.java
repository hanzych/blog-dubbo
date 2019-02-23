package com.yfancy.web.weixin.controller;


import com.yfancy.common.base.Result;
import com.yfancy.common.base.init.Weixin_Init_Param;
import com.yfancy.common.base.url.WEB_WEIXIN_URL_Mapping;
import com.yfancy.common.base.util.HttpClientUtil;
import com.yfancy.web.weixin.config.WeixinConfig;
import com.yfancy.web.weixin.helper.WeixinHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信模版类消息处理
 */
@RestController
@Slf4j
public class WxTempleteController {

    @Autowired
    private WeixinHelper weixinHelper;

    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_send_templete,method = RequestMethod.POST)
    public Result sendTempleteMsg(@RequestParam("userId") String userId,@RequestParam("templeteType") int templeteType){
        log.info("[WxTempleteController][sendTempleteMsg],发送模版信息，开始");

        String templeteContent = weixinHelper.createWeixinTempleteMsg(userId,templeteType);
        Result result = weixinHelper.sendWeixinTempleteMsg(templeteContent);
        return result;
    }
}
