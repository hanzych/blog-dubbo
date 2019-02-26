package com.yfancy.web.weixin.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 静态常量配置
 */
@Data
@Configuration  //证明这是一个配置类
@PropertySource(value = {"classpath:weixin_config_${spring.profiles.active}.properties"},encoding = "UTF-8", ignoreResourceNotFound = true)//可以放多个,{}里面用,分开
public class WeixinConfig {

    @Value("${token}")
    private String token;

    @Value("${appID}")
    private String appID;

    @Value("${appsecret}")
    private String appsecret;

    @Value("${access_token_url}")
    private String access_token_url;

    @Value("${create_menu_url}")
    private String create_menu_url;

    @Value("${get_menu_url}")
    private String get_menu_url;

    @Value("${del_menu_url}")
    private String del_menu_url;

    @Value("${media_upload_url}")
    private String media_upload_url;

    @Value("${add_material_url}")
    private String add_material_url;

    @Value("${init_text_resp_param}")
    private String init_text_resp_param;

     @Value("${subsriber_resp}")
    private String subsriber_resp;

     @Value("${unsubsriber_resp}")
    private String unsubsriber_resp;

     @Value("${send_template_url}")
    private String send_template_url;

}
