package com.yfancy.web.weixin.config;

import com.yfancy.web.weixin.init.WeixinInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 系统初始化一些参数到配置类
 */
@Component
public class DataInitConfig implements CommandLineRunner {

    @Autowired
    private WeixinInit weixinInit;

    @Override
    public void run(String... strings) throws Exception {
        weixinInit.initWeixinTextRespParam();
        weixinInit.initWeixinTempleteIdWithType();
        weixinInit.initWeixinMediaMap();
    }
}
