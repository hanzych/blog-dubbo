package com.yfancy.common.base.enums;

import com.alibaba.fastjson.JSONObject;

public enum  SystemCodeMsgEnum {

    success(200L, "处理成功"),
    error(999999L, "异常错误"),
    authError(-1L, "鉴权失败"),
    timeout(111111L, "超时异常"),
    dbinsert(600001, "数据插入异常"),
    dbupdate(600002, "数据更新异常"),

    kafkanooffset(500012, "kafka偏移量为空"),
    kafkaerror(500099, "kafka异常"),
    kafkanorusult(500201, "kafka无返回结果"),


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
