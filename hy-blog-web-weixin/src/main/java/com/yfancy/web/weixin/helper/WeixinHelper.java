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
import com.yfancy.web.weixin.vo.message.*;
import com.yfancy.web.weixin.vo.message.image.ImageReceiveMessage;
import com.yfancy.web.weixin.vo.message.image.ImageSendMessage;
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
     * @return mediaType  需要上传的媒体文件类型
     * @return uploadType  需要上传的种类，是临时媒体还是永久 1-临时  2-永久
     * @throws Exception
     */
    public String uploadMediaToWeixin(String urlPath, String mediaType, int uploadType) throws Exception {
        String accessToken = getWeixinAccessToken();
        String media_upload_url = "";
        if (uploadType == 1){
            media_upload_url = weixinConfig.getMedia_upload_url();
        }else {
            media_upload_url = weixinConfig.getAdd_material_url();
        }
        String url = String.format(media_upload_url, accessToken, mediaType);
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
        ImageSendMessage imageSendMessage = createMsgByType(imageReceiveMessage, WeixinMsgTypeEnum.image);
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
        if ("subscribe".equals(event)){
            log.info("[WeixinHelper][dealEventMsg],用户关注类订阅号...");
            //用户订阅之后，给他发送一个图文消息，说明次公众号的用途
            ArticlesMessage articlesMessage = createMsgByType(eventMsg, WeixinMsgTypeEnum.news);
            return articlesMessage;

        }
        if ("TEMPLATESENDJOBFINISH".equals(event)){
            String status = eventMsg.getStatus();
            if ("success".equals(status)){
                log.info("[WeixinHelper][dealEventMsg],用户已成功接受模版消息推送...");
            }
        }
        if ("CLICK".equals(event)){
            String eventKey = eventMsg.getEventKey();
            if ("today_music_key".equals(eventKey)){
                //如果是点击菜单按钮触发的，发送一个图文消息
                ArticlesMessage articlesMessage = createMsgByType(eventMsg, WeixinMsgTypeEnum.news);
                return articlesMessage;
            }else {
                TextMessage textMessage = createMsgByType(eventMsg,WeixinMsgTypeEnum.text);
                textMessage.setContent("对不起我们无法识别你的操作");
                return textMessage;
            }
        }
        return null;
    }

    /**
     * 处理文本消息
     * @return
     */
    private BaseMessage dealTextMsg(TextMessage msg) {
        String userSay = msg.getContent();//用户发来的信息
        if ("帮助,help".contains(userSay)){
            //给用户推送一些快捷使用关键词
            String content = " 1: 查看最近一次推送 \n 2: 查看推荐音乐 \n 3: 查看mac软件推荐 \n 4: 查看我能干啥 \n 5: 赞助一瓶汽水🥤";
            TextMessage textMessage = createMsgByType(msg, WeixinMsgTypeEnum.text);
            textMessage.setContent(content);
            return textMessage;

        }else if ("听歌,歌曲".contains(userSay)){
            MusicMessage musicMessage = createMsgByType(msg,WeixinMsgTypeEnum.music);
            return musicMessage;
        }
        else {
            //随机发送一个消息

            int size = Weixin_Init_Param.textRespParamMsg.size();
            Random random = new Random();
            int randomNum = random.nextInt(size - 1);
            String content = Weixin_Init_Param.textRespParamMsg.get(randomNum);
            TextMessage textMessage = createMsgByType(msg, WeixinMsgTypeEnum.text);
            textMessage.setContent(content);
            return textMessage;
        }
    }


    /**
     * 按照不同到类型创建对应到消息类
     * @param msg 为了知道发给谁
     * @param wantedMsgType 想要创建的消息的类型
     * @return
     */
    private <T> T createMsgByType(BaseMessage msg, WeixinMsgTypeEnum wantedMsgType){
        if (wantedMsgType == WeixinMsgTypeEnum.text){
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("");
            textMessage.setCreateTime(System.currentTimeMillis()/1000);
            textMessage.setFromUserName(msg.getToUserName());
            textMessage.setToUserName(msg.getFromUserName());
            textMessage.setMsgType(WeixinMsgTypeEnum.text.name());
            return (T) textMessage;
        }
        if (wantedMsgType == WeixinMsgTypeEnum.event){
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("");
            textMessage.setCreateTime(System.currentTimeMillis()/1000);
            textMessage.setFromUserName(msg.getToUserName());
            textMessage.setToUserName(msg.getFromUserName());
            textMessage.setMsgType(WeixinMsgTypeEnum.text.name());
            return (T) textMessage;
        }
        if (wantedMsgType == WeixinMsgTypeEnum.image){
            ImageSendMessage imageSendMessage = new ImageSendMessage();
            imageSendMessage.setCreateTime(System.currentTimeMillis()/1000);
            imageSendMessage.setFromUserName(msg.getToUserName());
            imageSendMessage.setToUserName(msg.getFromUserName());
            imageSendMessage.setMsgType(WeixinMsgTypeEnum.image.name());
            return (T) imageSendMessage;
        }
        if (wantedMsgType == WeixinMsgTypeEnum.news){
            ArticlesMessage articlesMessage = new ArticlesMessage();
            int count = 2;
            articlesMessage.setArticleCount(count);
            List<ArticlesVo> items = new ArrayList<>();
            ArticlesVo articlesVo = new ArticlesVo();
            articlesVo.setDescription("此公众号主要是分享一些好玩的东西，类型不限，听歌，学习，编程都会有。主要是记录自己学习中好玩的东西，分享出来。");
            articlesVo.setPicUrl("http://pic31.photophoto.cn/20140403/0017029551537896_b.jpg");
            articlesVo.setTitle("欢迎您订阅此公众号");
            articlesVo.setUrl("http://www.baidu.com");
            ArticlesVo articlesItem = new ArticlesVo();
            articlesItem.setDescription("mac入门软件推荐（一）");
            articlesItem.setPicUrl("http://imgsrc.baidu.com/imgad/pic/item/7e3e6709c93d70cfa2a88431f3dcd100baa12bbf.jpg");
            articlesItem.setTitle("第一篇：mac软件分享");
            articlesItem.setUrl("http://www.github.com");
            items.add(articlesVo);
            items.add(articlesItem);

            articlesMessage.setArticles(items);
            articlesMessage.setCreateTime(System.currentTimeMillis()/1000);
            articlesMessage.setFromUserName(msg.getToUserName());
            articlesMessage.setToUserName(msg.getFromUserName());
            articlesMessage.setMsgType(WeixinMsgTypeEnum.news.name());
            return (T) articlesMessage;

        }
        if (wantedMsgType == WeixinMsgTypeEnum.music){
            MusicMessage musicMessage = new MusicMessage();
            musicMessage.setCreateTime(System.currentTimeMillis()/1000);
            musicMessage.setFromUserName(msg.getToUserName());
            musicMessage.setToUserName(msg.getFromUserName());
            musicMessage.setMsgType(WeixinMsgTypeEnum.music.name());
            MusicVo musicVo = new MusicVo();
            musicVo.setDesc("无敌是多么的寂寞");
            musicVo.setTitle("无敌");
            musicVo.setThumbMediaId(Weixin_Init_Param.mediaMap.get("thumb").get(1));
            musicVo.setMusicUrl("http://ra01.sycdn.kuwo.cn/resource/n3/32/56/3260586875.mp3");
            musicMessage.setMusicVo(musicVo);
            return (T) musicMessage;
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
