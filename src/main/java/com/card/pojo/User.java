package com.card.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer parentId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 代理商名称
     */
    private String nickname;

    /**
     * 可用授权数
     */
    private Integer allCount;

    /**
     * 已用授权数
     */
    private Integer nowCount;

    /**
     * 代理等级
     */
    private Integer level;

    /**
     * 0正常，1锁定
     */
    private Integer status;

    /**
     * 类型
     */
    private String type;

    /**
     * 
     */
    private Date createTime;

    /**
     * 登录时间
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
     * 
     * @return parent_id 
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 
     * @param parentId 
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 用户名
     * @return username 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 
     * @return password 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 代理商名称
     * @return nickname 代理商名称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 代理商名称
     * @param nickname 代理商名称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 可用授权数
     * @return all_count 可用授权数
     */
    public Integer getAllCount() {
        return allCount;
    }

    /**
     * 可用授权数
     * @param allCount 可用授权数
     */
    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    /**
     * 已用授权数
     * @return now_count 已用授权数
     */
    public Integer getNowCount() {
        return nowCount;
    }

    /**
     * 已用授权数
     * @param nowCount 已用授权数
     */
    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }

    /**
     * 代理等级
     * @return level 代理等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 代理等级
     * @param level 代理等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 0正常，1锁定
     * @return status 0正常，1锁定
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0正常，1锁定
     * @param status 0正常，1锁定
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 登录时间
     * @return update_time 登录时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 登录时间
     * @param updateTime 登录时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}