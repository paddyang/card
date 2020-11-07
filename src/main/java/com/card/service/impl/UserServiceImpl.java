package com.card.service.impl;

import com.card.mapper.UserMapper;
import com.card.pojo.User;
import com.card.pojo.UserExample;
import com.card.service.UserService;
import com.card.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/7/3 20:28
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUsername(String username) {
        UserExample example=new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> owners = userMapper.selectByExample(example);
        return owners.size()==0?null:owners.get(0);
    }

    @Override
    public void updateUsedNum(int userId, int availableNum,int usedNum) {
        User owner=new User();
        owner.setId(userId);
        owner.setUsedNum(usedNum);
        owner.setAvailableNum(availableNum);
        userMapper.updateByPrimaryKeySelective(owner);
    }

    @Override
    public void editPass(int userId, String password) {
        User user=new User();
        user.setId(userId);
        user.setPassword(password);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> getByQuery(User user) {
        UserExample example = new UserExample();
        example.setOrderByClause("create_time desc");
        UserExample.Criteria criteria = example.createCriteria();
        if(null != user.getParentId()){
            criteria.andParentIdEqualTo(user.getParentId());
        }
        if(StringUtils.isNotBlank(user.getUsername()) ){
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if(null != user.getLevel()){
            criteria.andLevelEqualTo(user.getLevel());
        }
        if(null != user.getUsedNum()){
            criteria.andUsedNumEqualTo(user.getUsedNum());
        }
        if(null != user.getAvailableNum()){
            criteria.andAvailableNumEqualTo(user.getAvailableNum());
        }
        if(null != user.getStatus()){
            criteria.andStatusEqualTo(user.getStatus());
        }
        if(StringUtils.isNotBlank(user.getType())){
            criteria.andTypeEqualTo(user.getType());
        }
        return userMapper.selectByExample(example);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(MD5Utils.md5("123456"));
        user.setAvailableNum(0);
        user.setUsedNum(0);
        user.setStatus(0);
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(User user,int num, User phoneUser) throws Exception{
        User user1 = new User();
        user1.setId(user.getId());
        user1.setAvailableNum(user.getAvailableNum()-num);
        user1.setUsedNum(user.getUsedNum()+num);
        int i = userMapper.updateByPrimaryKeySelective(user1);
        if (i<1){
            throw new Exception();
        }
        User user2 = new User();
        user2.setId(phoneUser.getId());
        user2.setAvailableNum(phoneUser.getAvailableNum()+num);
        int b = userMapper.updateByPrimaryKeySelective(user2);
        if (b<1){
            throw new Exception();
        }
    }
}
