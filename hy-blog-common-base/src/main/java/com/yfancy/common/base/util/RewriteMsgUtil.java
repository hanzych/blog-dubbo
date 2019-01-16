package com.yfancy.common.base.util;

import com.alibaba.fastjson.JSONObject;
import com.yfancy.common.base.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class RewriteMsgUtil {

    /**
     * 发送消息 text/html;charset=utf-8
     * @param response
     * @param str
     * @throws Exception
     */
    public static void sendMessage(HttpServletResponse response, String str) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(str);
        writer.close();
        response.flushBuffer();
    }

    /**
     * 将某个对象转换成json格式并发送到客户端
     * @param response
     * @param result
     * @throws Exception
     */
    public static void writeJsonMsg(HttpServletResponse response, Result result) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(result));
        writer.close();
        response.flushBuffer();
    }

}
