package com.yfancy.web.weixin.helper;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yfancy.common.base.Result;
import com.yfancy.common.base.enums.SystemCodeMsgEnum;
import com.yfancy.common.base.enums.WeixinButtonTypeEnum;
import com.yfancy.common.base.enums.WeixinMsgTypeEnum;
import com.yfancy.common.base.init.Weixin_Init_Param;
import com.yfancy.common.base.util.HttpClientUtil;
import com.yfancy.common.base.util.SerializeXmlUtil;
import com.yfancy.web.weixin.config.WeixinConfig;
import com.yfancy.web.weixin.vo.menu.MenuButtonVo;
import com.yfancy.web.weixin.vo.menu.SubButtonVo;
import com.yfancy.web.weixin.vo.message.BaseMessage;
import com.yfancy.web.weixin.vo.message.EventMessage;
import com.yfancy.web.weixin.vo.message.image.ImageReceiveMessage;
import com.yfancy.web.weixin.vo.message.image.ImageSendMessage;
import com.yfancy.web.weixin.vo.message.TextMessage;
import com.yfancy.web.weixin.vo.message.image.ImageVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *微信帮助累
 */
@Component
@Slf4j
public class WeixinHelper {

    @Autowired
    private WeixinConfig weixinConfig;

    static Map<String,Object> accessTokenMap = new ConcurrentHashMap<String,Object>();


    /**
     * 获取微信accesstoken
     * @return
     */
    private String getWeixinAccessToken(){
        String accessToken = "";
        if (!accessTokenMap.containsKey("access_token")){
            accessToken = wxAccessToken();
        }else {
            //如果有记录，则判断是否过期
            Long expire_time = (Long) accessTokenMap.get("expire_time");
            Long now_time = System.currentTimeMillis();
            Long s = (now_time - expire_time)/(1000 * 60);
            if (s < 60){
                accessToken = (String) accessTokenMap.get("access_token");
            }else {
                accessToken = wxAccessToken();
            }
        }
        return accessToken;
    }

    /**
     * 调用微信接口获取accesstoken,不对外暴露
     * @return
     */
    private String wxAccessToken(){
        String appID = weixinConfig.getAppID();
        String appsecret = weixinConfig.getAppsecret();
        String access_token_url = weixinConfig.getAccess_token_url();
        String url = String.format(access_token_url, appID, appsecret);
        try {
            String result = HttpClientUtil.sendGet(url);
            String access_token = JSONObject.parseObject(result).getString("access_token");
            accessTokenMap.put("access_token",access_token);
            accessTokenMap.put("expire_time",System.currentTimeMillis());
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建微信菜单
     * @param content
     */
    public Result weixinCreateMenu(String content) {
        String create_menu_url = weixinConfig.getCreate_menu_url();
        String accessToken = getWeixinAccessToken();
        String url = String.format(create_menu_url, accessToken);
        try {
            log.info("[WeixinHelper][weixinCreateMenu],创建微信菜单，url={}",url);
            String result = HttpClientUtil.sendPost(url, content);
            log.info("[WeixinHelper][weixinCreateMenu],创建微信菜单，微信返回={}",result);
            Long errcode = JSONObject.parseObject(result).getLong("errcode");
            if (errcode == SystemCodeMsgEnum.weixin_success.getCode()){
                log.info("[WeixinHelper][weixinCreateMenu],创建微信菜单，创建成功");
                return Result.SUCCESS();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ERROR();
    }

    /**
     * 创建菜单内容
     * @return
     */
    @Test
    public String createMenuContent() {
        List<MenuButtonVo> button = new ArrayList<>();
        MenuButtonVo menuButtonVo1 = new MenuButtonVo(WeixinButtonTypeEnum.click.name(),"Today music","today_music_key",null);
        SubButtonVo subButtonVo1 = new SubButtonVo(WeixinButtonTypeEnum.view.name(),"搜索",null,
                "http://www.soso.com/",null,null,null);
        SubButtonVo subButtonVo2 = new SubButtonVo(WeixinButtonTypeEnum.miniprogram.name(),"小程序",null,
                "http://mp.weixin.qq.com","wx286b93c14bbf93aa","pages/lunar/index",null);
        SubButtonVo subButtonVo3 = new SubButtonVo(WeixinButtonTypeEnum.click.name(),"点赞","prise",
                null,null,null,null);
        List<SubButtonVo> subButtonVoList = new ArrayList<>();
        subButtonVoList.add(subButtonVo1);
        subButtonVoList.add(subButtonVo2);
        subButtonVoList.add(subButtonVo3);
        MenuButtonVo menuButtonVo2 = new MenuButtonVo(null,"menu",null,subButtonVoList);
        button.add(menuButtonVo1);
        button.add(menuButtonVo2);
        Map<String,List<MenuButtonVo>> map = new HashMap<>();
        map.put("button",button);
        String content = JSON.toJSONString(map);
        log.info("[WeixinHelper][createMenuContent],创建菜单的数据为={}",content);
        return content;
    }


    /**
     * 获取微信菜单
     * @return
     */
    public Result weixinGetMenu() {
        String weixinAccessToken = getWeixinAccessToken();
        String get_menu_url = weixinConfig.getGet_menu_url();
        String url = String.format(get_menu_url, weixinAccessToken);
        try {
            String result = HttpClientUtil.sendGet(url);
//            JSONObject resultJson = JSONObject.parseObject(result);
            return Result.SUCCESS(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ERROR();
    }

    /**
     * 删除微信菜单
     * @return
     */
    public Result weixinDelMenu() {
        String weixinAccessToken = getWeixinAccessToken();
        String del_menu_url = weixinConfig.getDel_menu_url();
        String url = String.format(del_menu_url, weixinAccessToken);
        try {
            String result = HttpClientUtil.sendGet(url);
            Long errcode = JSONObject.parseObject(result).getLong("errcode");
            if (errcode == SystemCodeMsgEnum.weixin_success.getCode()){
                return Result.SUCCESS();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ERROR();
    }


    /**
     * 网络图片上传到微信服务器
     *
     * @param urlPath 图片路径
     * @return media_id
     * @throws Exception
     */
    public String uploadMediaToWeixin(String urlPath, String fileType) throws Exception {
        String accessToken = getWeixinAccessToken();
        String media_upload_url = weixinConfig.getMedia_upload_url();
        String url = String.format(media_upload_url, accessToken, fileType);
        String result = HttpClientUtil.upload(url, urlPath);
        log.info("[WeixinHelper][uploadMediaToWeixin],result = {}", result);
//        JSONObject jsonObj = JSON.parseObject(result);
//        log.info("getMediaId jsonObj: {}", jsonObj);
//        return jsonObj.getString("media_id");
        return result;
    }

    /**
     * 根据消息类型，转换成对应的枚举
     * @param msgType
     * @return
     */
    private WeixinMsgTypeEnum returnMsgType(String msgType){
        for (WeixinMsgTypeEnum enums : WeixinMsgTypeEnum.values()) {
            if (enums.name().equals(msgType)) {
                return enums;
            }
        }
        return null;
    }


    /**
     * 根据用户的消息类型，返回相应的消息给用户
     * @param msgType 消息类型
     * @param xml 微信服务器传来到消息报文
     * @return
     */
    public BaseMessage replyAllTypeMsg(String msgType, String xml) {
        BaseMessage result = null;
        WeixinMsgTypeEnum weixinMsgTypeEnum = returnMsgType(msgType);
        switch (weixinMsgTypeEnum){
            case  text :
                TextMessage textMsg = SerializeXmlUtil.xmlToBean(xml, TextMessage.class);
                result = dealTextMsg(textMsg);
                break;
            case  image :
                ImageReceiveMessage imageReceiveMessage = SerializeXmlUtil.xmlToBean(xml, ImageReceiveMessage.class);
                result = dealImageMsg(imageReceiveMessage);
                break;
            case  music :
//                dealTextMsg();
                break;
            case  voice :
//                dealTextMsg();
                break;
            case  video :
//                dealTextMsg();
                break;
            case  event :
                EventMessage eventMsg = SerializeXmlUtil.xmlToBean(xml, EventMessage.class);
                result = dealEventMsg(eventMsg);
                break;
        }


        return result;


    }


    /**
     * 处理事件消息到具体到业务
     * @param imageReceiveMessage
     * @return
     */
    private BaseMessage dealImageMsg(ImageReceiveMessage imageReceiveMessage) {
        ImageSendMessage imageSendMessage = createMsgByType(imageReceiveMessage);
        imageSendMessage.setImage(new ImageVo("lQ88oxpGbrcRV3dN2n6_vDMFZHvugobfhAWldH5T0YeLZq6uNxOeR1NuvsFH-de4"));
        return imageSendMessage;
    }

    /**
     * 处理事件消息到具体到业务
     * @param eventMsg
     * @return
     */
    private BaseMessage dealEventMsg(EventMessage eventMsg) {
        String event = eventMsg.getEvent();
        if ("unsubscribe".equals(event)){
            log.info("[WeixinHelper][dealEventMsg],用户取消关注类订阅号...");
            //用户订阅之后，给他发送一个消息
            String content = weixinConfig.getUnsubsriber_resp();
            TextMessage textMessage = createMsgByType(eventMsg);
            textMessage.setContent(content);
            return textMessage;
        }
        if ("subscribe".equals(event)){
            log.info("[WeixinHelper][dealEventMsg],用户关注类订阅号...");
            //用户订阅之后，给他发送一个消息
            String content = weixinConfig.getSubsriber_resp();
            TextMessage textMessage = createMsgByType(eventMsg);
            textMessage.setContent(content);
            return textMessage;
        }
        if ("TEMPLATESENDJOBFINISH".equals(event)){
            String status = eventMsg.getStatus();
            if ("success".equals(status)){
                log.info("[WeixinHelper][dealEventMsg],用户已成功接受模版消息推送...");
            }
        }
        return null;
    }

    /**
     * 处理文本消息
     * @return
     */
    private TextMessage dealTextMsg(BaseMessage msg) {
        int size = Weixin_Init_Param.textRespParamMsg.size();
        Random random = new Random();
        int randomNum = random.nextInt(size - 1);
        String content = Weixin_Init_Param.textRespParamMsg.get(randomNum);
        TextMessage textMessage = createMsgByType(msg);
        textMessage.setContent(content);
        return textMessage;
    }


    /**
     * 按照不同到类型创建对应到消息类
     * @param msg 为了知道发给谁
     * @return
     */
    private <T> T createMsgByType(BaseMessage msg){
        WeixinMsgTypeEnum weixinMsgTypeEnum = returnMsgType(msg.getMsgType());
        if (weixinMsgTypeEnum == WeixinMsgTypeEnum.text){
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("");
            textMessage.setCreateTime(System.currentTimeMillis()/1000);
            textMessage.setFromUserName(msg.getToUserName());
            textMessage.setToUserName(msg.getFromUserName());
            textMessage.setMsgType(WeixinMsgTypeEnum.text.name());
            return (T) textMessage;
        }
        if (weixinMsgTypeEnum == WeixinMsgTypeEnum.event){
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("");
            textMessage.setCreateTime(System.currentTimeMillis()/1000);
            textMessage.setFromUserName(msg.getToUserName());
            textMessage.setToUserName(msg.getFromUserName());
            textMessage.setMsgType(WeixinMsgTypeEnum.text.name());
            return (T) textMessage;
        }
        if (weixinMsgTypeEnum == WeixinMsgTypeEnum.image){
            ImageSendMessage imageSendMessage = new ImageSendMessage();
//            imageSendMessage.setImage(new ImageVo("0zeU3wDu7MM9tIVGCpEc79EU0KCC1dxo3PiIUuA7jWzM0Kh"));
            imageSendMessage.setCreateTime(System.currentTimeMillis()/1000);
            imageSendMessage.setFromUserName(msg.getToUserName());
            imageSendMessage.setToUserName(msg.getFromUserName());
            imageSendMessage.setMsgType(WeixinMsgTypeEnum.image.name());
            return (T) imageSendMessage;
        }

        return null;
    }

    /**
     * 从微信发来的报文中获取msgtype
     * @param xml
     * @return
     */
    public String getMsgTypeFromWixinReq(String xml) {
        if (xml.indexOf("MsgType") != -1){
            int start = xml.indexOf("<MsgType>");
            int end = xml.indexOf("</MsgType>");
            String msgType = xml.substring(start + 18, end - 3);
            return msgType;
        }
        return null;
    }

    /**
     * 创建微信模版发送信息
     * @param userId 微信id
     * @param templeteType 模版类型
     * @return
     */
    public String createWeixinTempleteMsg(String userId, int templeteType) {
        String templeteid = Weixin_Init_Param.templeteMap.get(templeteType);
        Map<String,Object> templeteMap = new LinkedHashMap<>();
        templeteMap.put("touser",userId);
        templeteMap.put("template_id",templeteid);
        templeteMap.put("url","http://www.baidu.com");
//        Map<String,Object> miniprogram = new HashMap<>();
//        miniprogram.put("appid","xiaochengxuappid12345");
//        miniprogram.put("pagepath","index?foo=bar");
//        templeteMap.put("miniprogram",miniprogram);
        Map<String,Object> dataMap = new LinkedHashMap<>();
        Map<String,Object> firstMap = new LinkedHashMap<>();
        firstMap.put("value","恭喜你购买成功！");
        firstMap.put("color","#173177");
        dataMap.put("first",firstMap);
        Map<String,Object> keyword1Map = new LinkedHashMap<>();
        keyword1Map.put("value","巧克力");
        keyword1Map.put("color","#173177");
        dataMap.put("card",keyword1Map);
        Map<String,Object> keyword2Map = new LinkedHashMap<>();
        keyword2Map.put("value","39.8元");
        keyword2Map.put("color","#173177");
        dataMap.put("name",keyword2Map);
        Map<String,Object> keyword3Map = new LinkedHashMap<>();
        keyword3Map.put("value",new Date());
        keyword3Map.put("color","#173177");
        dataMap.put("time",keyword3Map);
        Map<String,Object> remarkMap = new LinkedHashMap<>();
        remarkMap.put("value","欢迎再次购买！");
        remarkMap.put("color","#173177");
        dataMap.put("remark",remarkMap);

        templeteMap.put("data",dataMap);
        String content = JSON.toJSONString(templeteMap);
        log.info("模版消息={}",content);
        return content;

    }

    /**
     * 发送模版消息到微信
     */
    public Result sendWeixinTempleteMsg(String templeteMsg) {
        String accessToken = getWeixinAccessToken();
        String send_template_url = weixinConfig.getSend_template_url();
        String url = String.format(send_template_url, accessToken);
        try {
            String result = HttpClientUtil.sendPost(url, templeteMsg);
            Long errcode = JSONObject.parseObject(result).getLong("errcode");
            if (errcode == SystemCodeMsgEnum.weixin_success.getCode()){
                return Result.SUCCESS();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ERROR();
    }
}
