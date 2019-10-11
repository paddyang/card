package com.card.pojo;

import java.util.Date;

public class LogCheck {
    /**
     * 
     */
    private Long id;

    /**
     * 卡密
     */
    private String card;

    /**
     * 卡密类型
     */
    private String cardType;

    /**
     * 验证类型，1未激活验证，2已激活验证
     */
    private String checkType;

    /**
     * 
     */
    private Date created;

    /**
     * 状态，1成功，2失败
     */
    private String status;

    /**
     * 验证结果备注
     */
    private String mark;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 卡密
     * @return card 卡密
     */
    public String getCard() {
        return card;
    }

    /**
     * 卡密
     * @param card 卡密
     */
    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
    }

    /**
     * 卡密类型
     * @return card_type 卡密类型
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * 卡密类型
     * @param cardType 卡密类型
     */
    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    /**
     * 验证类型，1未激活验证，2已激活验证
     * @return check_type 验证类型，1未激活验证，2已激活验证
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * 验证类型，1未激活验证，2已激活验证
     * @param checkType 验证类型，1未激活验证，2已激活验证
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    /**
     * 
     * @return created 
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 
     * @param created 
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 状态，1成功，2失败
     * @return status 状态，1成功，2失败
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态，1成功，2失败
     * @param status 状态，1成功，2失败
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 验证结果备注
     * @return mark 验证结果备注
     */
    public String getMark() {
        return mark;
    }

    /**
     * 验证结果备注
     * @param mark 验证结果备注
     */
    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public LogCheck() {
    }

    public LogCheck(String card, String cardType, String checkType, Date created, String status, String mark) {
        this.card = card;
        this.cardType = cardType;
        this.checkType = checkType;
        this.created = created;
        this.status = status;
        this.mark = mark;
    }
}