package com.yfancy.common.base.enums;

import com.alibaba.fastjson.JSONObject;

public enum  SystemCodeMsgEnum {

    success(200L, "处理成功"),
    error(999999L, "异常错误"),
    authError(-1L, "鉴权失败"),
    timeout(111111L, "超时异常")


    ;


    private long code;
    private String msg;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    SystemCodeMsgEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
