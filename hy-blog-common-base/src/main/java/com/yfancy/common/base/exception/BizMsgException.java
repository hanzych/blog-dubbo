package com.yfancy.common.base.exception;

import com.yfancy.common.base.Result;
import com.yfancy.common.base.enums.SystemCodeMsgEnum;

/**
 * 自定义异常
 */
public class BizMsgException extends RuntimeException {

    public final static BizMsgException TIME_OUT_EXCEPTION = new BizMsgException(SystemCodeMsgEnum.timeout);

    public final static BizMsgException DB_INSERT_EXCEPTION = new BizMsgException(SystemCodeMsgEnum.dbinsert);

    public final static BizMsgException DB_UPDATE_EXCEPTION = new BizMsgException(SystemCodeMsgEnum.dbupdate);

    public BizMsgException(Result apiResponse, Exception exception){
        this.apiResponse = apiResponse;
        this.exception = exception;
    }

    public BizMsgException(SystemCodeMsgEnum systemCodeMsgEnum){
        this.apiResponse = Result.ERROR(systemCodeMsgEnum);
        this.exception = new RuntimeException(systemCodeMsgEnum.getMsg());
    }

    public BizMsgException(SystemCodeMsgEnum systemCodeMsgEnum, Object object){
        this.apiResponse = Result.ERROR(systemCodeMsgEnum,object);
        this.exception = new RuntimeException(systemCodeMsgEnum.getMsg());
    }
 
    private Exception exception;
    private Result apiResponse;

    public Exception getException() {
        return exception;
    }
 
    public void setException(Exception exception) {
        this.exception = exception;
    }
 
    public Result getApiResponse() {
        return apiResponse;
    }
 
    public void setApiResponse(Result apiResponse) {
        this.apiResponse = apiResponse;
    }
}

