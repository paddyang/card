package com.card.utils;

import java.io.Serializable;


public class BaseResp implements Serializable {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;


    public static BaseResp build(Integer status, String msg, Object data) {
        return new BaseResp(status, msg);
    }

    public static BaseResp success(String msg) {
        return new BaseResp(msg);
    }

    public static BaseResp success() {
        return new BaseResp(null);
    }

    public BaseResp() {

    }

    public static BaseResp build(Integer code, String msg) {
        return new BaseResp(code, msg);
    }

    public BaseResp(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResp(String msg) {
        this.code = 200;
        this.msg = msg;
    }


    public Integer getStatus() {
        return code;
    }

    public void setStatus(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
