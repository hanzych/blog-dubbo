package com.yfancy.web.boss.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yfancy.common.base.Result;
import com.yfancy.common.base.url.WEB_BOSS_URL_Mapping;
import com.yfancy.service.notify.api.service.NotifyRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NotifyController {

    @Reference
    private NotifyRpcService notifyRpcService;

    @RequestMapping(value = WEB_BOSS_URL_Mapping.NOTIFY_SEND_MSG, method = RequestMethod.GET)
    public Result sendMsgTest(@RequestParam String msg){
        notifyRpcService.sendMessage(msg);
        log.info("信息发送完成");
        return Result.SUCCESS();
    }

}
