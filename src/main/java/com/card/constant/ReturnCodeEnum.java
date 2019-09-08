package com.card.constant;

/**
 * @author: yangPan
 * @date: 2019/7/6 16:24
 * @description:
 */
public enum ReturnCodeEnum {

    /**
     * 激活成功
     */
    OK("激活成功","激活成功"),

    /**
     * 激活码错误
     */
    ERROR("激活码错误","激活码错误"),

    /**
     * 代理账号封停
     */
    E0("验证失败0","代理账号封停"),

    /**
     * 激活码被锁定
     */
    E1("验证失败1","激活码被锁定"),

    /**
     * 激活码与绑定机器不一致
     */
    E2("激活码与绑定手机不一致","激活码与绑定手机不一致"),

    /**
     * 激活码类型错误
     */
    E4("激活码类型错误","激活码类型错误"),

    /**
     * 激活码过期
     */
    E3("激活码过期","激活码过期"),

    /**
     * 激活码过期
     */
    E5("激活码不存在","激活码不存在"),

    /**
     * 验证异常
     */
    E9999("验证失败e","验证异常");

    private String code;

    private String desc;

    ReturnCodeEnum(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    ReturnCodeEnum() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
