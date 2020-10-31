package com.card.pojo.wwj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PlayerInfo implements Serializable {

    private String account;
    private Date loginTime;
    private boolean isbidden;
    private String platform;
    private String region;
    private String city;
    private String playerId;
    private String ip;
    private String secret;
    private int invitations;
    private String ivtCode;
    private int balance;
    private boolean status;
    private String channelGid;
    private List<Object> info;
    private String expires;
    private Date createTime;
    private String features;
    private boolean avtCodeStatus;
    private boolean showRechgPage;
    private boolean showFeedback;
    private boolean showBulletin;
    private boolean showSharePage;
    private boolean showIvtPage;
    private boolean marketManageStatus;
    private String zombieDectectMsg;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isIsbidden() {
        return isbidden;
    }

    public void setIsbidden(boolean isbidden) {
        this.isbidden = isbidden;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getInvitations() {
        return invitations;
    }

    public void setInvitations(int invitations) {
        this.invitations = invitations;
    }

    public String getIvtCode() {
        return ivtCode;
    }

    public void setIvtCode(String ivtCode) {
        this.ivtCode = ivtCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getChannelGid() {
        return channelGid;
    }

    public void setChannelGid(String channelGid) {
        this.channelGid = channelGid;
    }


    public List<Object> getInfo() {
        return info;
    }

    public void setInfo(List<Object> info) {
        this.info = info;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public boolean isAvtCodeStatus() {
        return avtCodeStatus;
    }

    public void setAvtCodeStatus(boolean avtCodeStatus) {
        this.avtCodeStatus = avtCodeStatus;
    }

    public boolean isShowRechgPage() {
        return showRechgPage;
    }

    public void setShowRechgPage(boolean showRechgPage) {
        this.showRechgPage = showRechgPage;
    }

    public boolean isShowFeedback() {
        return showFeedback;
    }

    public void setShowFeedback(boolean showFeedback) {
        this.showFeedback = showFeedback;
    }

    public boolean isShowBulletin() {
        return showBulletin;
    }

    public void setShowBulletin(boolean showBulletin) {
        this.showBulletin = showBulletin;
    }

    public boolean isShowSharePage() {
        return showSharePage;
    }

    public void setShowSharePage(boolean showSharePage) {
        this.showSharePage = showSharePage;
    }

    public boolean isShowIvtPage() {
        return showIvtPage;
    }

    public void setShowIvtPage(boolean showIvtPage) {
        this.showIvtPage = showIvtPage;
    }

    public boolean isMarketManageStatus() {
        return marketManageStatus;
    }

    public void setMarketManageStatus(boolean marketManageStatus) {
        this.marketManageStatus = marketManageStatus;
    }

    public String getZombieDectectMsg() {
        return zombieDectectMsg;
    }

    public void setZombieDectectMsg(String zombieDectectMsg) {
        this.zombieDectectMsg = zombieDectectMsg;
    }
}
