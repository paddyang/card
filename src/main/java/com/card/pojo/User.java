package com.card.pojo;

import com.card.utils.GsonUtils;

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
    private Integer availableNum;

    /**
     * 已用授权数
     */
    private Integer usedNum;

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
     * @return available_num 可用授权数
     */
    public Integer getAvailableNum() {
        return availableNum;
    }

    /**
     * 可用授权数
     * @param availableNum 可用授权数
     */
    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    /**
     * 已用授权数
     * @return used_num 已用授权数
     */
    public Integer getUsedNum() {
        return usedNum;
    }

    /**
     * 已用授权数
     * @param usedNum 已用授权数
     */
    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
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

    @Override
    public String toString() {
        return GsonUtils.GsonString(this);
    }
}