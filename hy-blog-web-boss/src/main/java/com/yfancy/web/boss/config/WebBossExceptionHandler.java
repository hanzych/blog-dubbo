package com.yfancy.web.boss.config;

import com.yfancy.common.base.Result;
import com.yfancy.common.base.enums.SystemCodeMsgEnum;
import com.yfancy.common.base.exception.BizMsgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * admin全局异常处理
 */
@Slf4j
@ControllerAdvice
public class WebBossExceptionHandler {
 
    /**
      * @Author: han
      * @Description: 系统异常捕获处理
      * @Date: 16:07 2018/5/30
      */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result javaExceptionHandler(Exception ex) {//APIResponse是项目中对外统一的出口封装，可以根据自身项目的需求做相应更改
        log.error("捕获到Exception异常",ex);
        //异常日志入库
        return new Result(SystemCodeMsgEnum.error,ex.getMessage());
    }
 
    /**
      * @Author: han
      * @Description: 自定义异常捕获处理
      * @Date: 16:08 2018/5/30
      */
    @ResponseBody
    @ExceptionHandler(value = BizMsgException.class)//MessageCenterException是自定义的一个异常
    public Result messageCenterExceptionHandler(BizMsgException ex) {
        log.error("捕获到MessageCenterException异常",ex.getException());
        //异常日志入库
        return ex.getApiResponse();
    }
 
}

