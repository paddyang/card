package com.card.pojo;

/**
 * @author: yangPan
 * @date: 2019/7/6 18:08
 * @description: 卡密详情
 */
public class CardInfo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 总数
     */
    private int allCard;

    /**
     * 可赠送数量
     */
    private int remainCard;

    /**
     * 已用数量
     */
    private int usedCard;

    /**
     * 未用数量
     */
    private int notUsedCard;

    /**
     * 时间
     */
    private String dateStr;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAllCard() {
        return allCard;
    }

    public void setAllCard(int allCard) {
        this.allCard = allCard;
    }

    public int getRemainCard() {
        return remainCard;
    }

    public void setRemainCard(int remainCard) {
        this.remainCard = remainCard;
    }

    public int getUsedCard() {
        return usedCard;
    }

    public void setUsedCard(int usedCard) {
        this.usedCard = usedCard;
    }

    public int getNotUsedCard() {
        return notUsedCard;
    }

    public void setNotUsedCard(int notUsedCard) {
        this.notUsedCard = notUsedCard;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
