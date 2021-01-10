package com.card.pojo;

import com.card.utils.GsonUtils;

import java.io.Serializable;
import java.util.Date;

public class CardType implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 类型
     */
    private String cardType;

    /**
     * 类型名字
     */
    private String name;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String status;

    /**
     * 
     * @return id 
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
     * 类型
     * @return card_type 类型
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * 类型
     * @param cardType 类型
     */
    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    /**
     * 类型名字
     * @return name 类型名字
     */
    public String getName() {
        return name;
    }

    /**
     * 类型名字
     * @param name 类型名字
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * @return status 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public String toString() {
        return GsonUtils.GsonString(this);
    }
}