package com.card.constant;

/**
 * @author: yangPan
 * @date: 2019/7/20 19:04
 * @description:
 */
public enum TypeEnum {

    ALL("ALL","所有"),
    FFSD("FFSD","防封时代"),
    AZCT("AZCT","安卓春天"),
    BLZS("BLZS","菠萝助手"),
    SX("SX","随心");

    private String code;
    private String desc;

    TypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
