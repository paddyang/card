package com.card.pojo;

import com.card.utils.GsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Card implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 设备号
     */
    private String uid;

    /**
     * 使用时间
     */
    private Date useTime;

    /**
     * 0未使用，1已使用，2已锁定
     */
    private Integer status;

    /**
     * 所有者
     */
    private Integer userId;

    /**
     * 
     */
    private Integer days;

    /**
     * 安全码
     */
    private String safeCode;

    /**
     * 
     */
    private Integer isOk;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 
     * @return Id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 卡号
     * @return card_no 卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 卡号
     * @param cardNo 卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * 设备号
     * @return uid 设备号
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设备号
     * @param uid 设备号
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 使用时间
     * @return use_time 使用时间
     */
    public Date getUseTime() {
        return useTime;
    }

    /**
     * 使用时间
     * @param useTime 使用时间
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    /**
     * 0未使用，1已使用，2已锁定
     * @return status 0未使用，1已使用，2已锁定
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0未使用，1已使用，2已锁定
     * @param status 0未使用，1已使用，2已锁定
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 所有者
     * @return user_id 所有者
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 所有者
     * @param userId 所有者
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return days 
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 
     * @param days 
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 安全码
     * @return safe_code 安全码
     */
    public String getSafeCode() {
        return safeCode;
    }

    /**
     * 安全码
     * @param safeCode 安全码
     */
    public void setSafeCode(String safeCode) {
        this.safeCode = safeCode == null ? null : safeCode.trim();
    }

    /**
     * 
     * @return is_ok 
     */
    public Integer getIsOk() {
        return isOk;
    }

    /**
     * 
     * @param isOk 
     */
    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    /**
     * 生成时间
     * @return create_time 生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 生成时间
     * @param createTime 生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Card() {
    }

    public Card(String cardNo,Integer status,Date createTime, Integer userId,  Integer isOk, Integer days) {
        this.cardNo = cardNo;
        this.status = status;
        this.userId = userId;
        this.days = days;
        this.isOk = isOk;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return GsonUtils.GsonString(this);
    }
}