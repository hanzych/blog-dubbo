package com.yfancy.common.base;


import com.yfancy.common.base.enums.SystemCodeMsgEnum;
import lombok.Data;

@Data
public class Result {

    private long code;

    private String msg;

    private Object data;

    public Result(long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(SystemCodeMsgEnum systemCodeMsgEnum, Object data) {
        this.code = systemCodeMsgEnum.getCode();
        this.msg = systemCodeMsgEnum.getMsg();
        this.data = data;
    }



    public static Result SUCCESS(){
        return new Result(SystemCodeMsgEnum.success,null);
    }

    public static Result SUCCESS(Object data){
        return new Result(SystemCodeMsgEnum.success,data);
    }

    public static Result ERROR(SystemCodeMsgEnum systemCodeMsgEnum){
        return new Result(systemCodeMsgEnum,null);
    }

    public static Result ERROR(SystemCodeMsgEnum systemCodeMsgEnum,Object object){
        return new Result(systemCodeMsgEnum,object);
    }

    public static Result ERROR(){
        return new Result(SystemCodeMsgEnum.error,null);
    }
}
