package com.yfancy.web.boss.interceptor;

import com.yfancy.common.base.Result;
import com.yfancy.common.base.enums.SystemCodeMsgEnum;
import com.yfancy.common.base.util.RewriteMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("【AuthInterceptor】【preHandle】，鉴权开始，判断鉴权是否通过");
        String noauth = httpServletRequest.getHeader("NOAUTH");
        if (noauth != null){
            log.info("【AuthInterceptor】【preHandle】，不需要鉴权，通过");
            return true;
        }


        log.info("【AuthInterceptor】【preHandle】，鉴权失败");
        RewriteMsgUtil.writeJsonMsg(httpServletResponse, Result.ERROR(SystemCodeMsgEnum.authError));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
