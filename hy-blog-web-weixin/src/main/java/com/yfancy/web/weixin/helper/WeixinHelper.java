package com.yfancy.web.weixin.helper;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yfancy.common.base.Result;
import com.yfancy.common.base.enums.SystemCodeMsgEnum;
import com.yfancy.common.base.enums.WeixinButtonTypeEnum;
import com.yfancy.common.base.enums.WeixinMsgTypeEnum;
import com.yfancy.common.base.util.HttpClientUtil;
import com.yfancy.web.weixin.config.WeixinConfig;
import com.yfancy.web.weixin.vo.menu.MenuButtonVo;
import com.yfancy.web.weixin.vo.menu.SubButtonVo;
import com.yfancy.web.weixin.vo.message.BaseMessage;
import com.yfancy.web.weixin.vo.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
     * @param msg
     * @return
     */
    public BaseMessage replyMsgToUser(BaseMessage msg) {
        BaseMessage result = null;
        WeixinMsgTypeEnum weixinMsgTypeEnum = returnMsgType(msg.getMsgType());
        switch (weixinMsgTypeEnum){
            case  text :
                result = dealTextMsg(msg);
                break;
            case  image :
//                dealTextMsg();
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
        }


        return result;


    }

    /**
     * 处理文本消息
     * @return
     */
    private TextMessage dealTextMsg(BaseMessage msg) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent("傻啊你");
        textMessage.setCreateTime(System.currentTimeMillis()/1000);
        textMessage.setFromUserName(msg.getToUserName());
        textMessage.setToUserName(msg.getFromUserName());
        textMessage.setMsgType(WeixinMsgTypeEnum.text.name());

        return textMessage;
    }
}
