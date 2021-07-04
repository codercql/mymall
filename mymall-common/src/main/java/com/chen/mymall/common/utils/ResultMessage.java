package com.chen.mymall.common.utils;


import lombok.Data;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 16:16
 * @Description:
 */
//@Component
@Data
public class ResultMessage {

    private String code;
    private String msg;
    private Object data;

    public static ResultMessage success(String code, String msg, Object data) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.code=code;
        resultMessage.msg=msg;
        resultMessage.data = data;
        return resultMessage;
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
    }

    public  static ResultMessage success(String code, String msg) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.code=code;
        resultMessage.msg=msg;
        resultMessage.data = null;
        return resultMessage;
//        this.code = code;
//        this.msg = msg;
//        this.data = null;
    }

    public static ResultMessage success(String code, Object data) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.code=code;
        resultMessage.msg=null;
        resultMessage.data = data;
        return resultMessage;
//        this.code = code;
//        this.msg = null;
//        this.data = data;
    }

    public static ResultMessage fail(String code, String msg) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.code=code;
        resultMessage.msg=msg;
        resultMessage.data = null;
        return resultMessage;
//        this.code = code;
//        this.msg = msg;
//        this.data = null;
    }

}
