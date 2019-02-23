package com.yfancy.web.weixin.controller;


import com.yfancy.common.base.Result;
import com.yfancy.common.base.url.WEB_WEIXIN_URL_Mapping;
import com.yfancy.common.base.util.CommonUtil;
import com.yfancy.common.base.util.SerializeXmlUtil;
import com.yfancy.web.weixin.config.WeixinConfig;
import com.yfancy.web.weixin.helper.WeixinHelper;
import com.yfancy.web.weixin.vo.message.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class WxAccessController {

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private WeixinHelper weixinHelper;


    /**
     * 微信初始化接入
     * @param request
     * @return
     */
    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_access, method = RequestMethod.GET)
    public String weixinAccess(HttpServletRequest request){
        log.info("[WxAccessController][weixinAccess]接入微信，开始");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String dictSort = CommonUtil.dictSort(timestamp, nonce, weixinConfig.getToken());
        log.info("dictSort = {}",dictSort);
        String sha1 = CommonUtil.sha1(dictSort);
        log.info("sha1 = {}",sha1);
        boolean flag =  sha1 != null ? sha1.equalsIgnoreCase(signature) : false;
        if (flag){
            log.info("[WxAccessController][weixinAccess]接入微信，接入成功！");
            return echostr;
        }

        log.info("[WxAccessController][weixinAccess]接入微信，接入失败！");
        return null;
    }


    /**
     * 创建微信菜单
     * @return
     */
    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_create_menu, method = RequestMethod.POST)
    public Result weixinCreateMenu(){
        log.info("[WxAccessController][weixinCreateMenu]创建微信菜单，开始！");
        String content = weixinHelper.createMenuContent();
        Result result = weixinHelper.weixinCreateMenu(content);
        log.info("[WxAccessController][weixinCreateMenu]创建微信菜单，结束，result={}", result);
        return result;
    }

    /**
     * 获取微信菜单
     * @return
     */
    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_get_menu, method = RequestMethod.GET)
    public Result weixinGetMenu(){
        log.info("[WxAccessController][weixinGetMenu]获取微信菜单，开始！");
        Result result = weixinHelper.weixinGetMenu();
        log.info("[WxAccessController][weixinGetMenu]获取微信菜单，结束，result={}",result);
        return result;
    }

    /**
     * 删除微信菜单
     * @return
     */
    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_del_menu, method = RequestMethod.GET)
    public Result weixinDelMenu(){
        log.info("[WxAccessController][weixinDelMenu]删除微信菜单，开始！");
        Result result = weixinHelper.weixinDelMenu();
        log.info("[WxAccessController][weixinDelMenu]删除微信菜单，结束，result={}",result);
        return result;
    }


    /**
     * 微信消息处理
     * @param request
     * @return
     */
    @RequestMapping(value = WEB_WEIXIN_URL_Mapping.weixin_access, method = RequestMethod.POST)
    public String weixinMessage(HttpServletRequest request){
        String xml = CommonUtil.getRequestContent(request);
        log.info("[WxAccessController][weixinMessage],处理消息，message = {}", xml);
        String msgType = weixinHelper.getMsgTypeFromWixinReq(xml);
        BaseMessage baseMessage = weixinHelper.replyAllTypeMsg(msgType,xml);
        if (baseMessage == null){
            return "";
        }
        String beanToXml = SerializeXmlUtil.beanToXml(baseMessage);
        log.info("[WxAccessController][weixinMessage],处理消息，完成");
        return beanToXml;
    }


}
