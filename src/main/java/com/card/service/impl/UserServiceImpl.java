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
    public void updateNowCount(int userId, int nowCount) {
        User owner=new User();
        owner.setId(userId);
        owner.setNowCount(nowCount);
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
        if(null != user.getNowCount()){
            criteria.andNowCountEqualTo(user.getNowCount());
        }
        if(null != user.getAllCount()){
            criteria.andAllCountEqualTo(user.getAllCount());
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
        user.setAllCount(0);
        user.setNowCount(0);
        user.setStatus(0);
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(User user,int num, User phoneUser) throws Exception{
        User user1 = new User();
        user1.setId(user.getId());
        user1.setAllCount(user.getAllCount()-num);
        user1.setNowCount(user.getNowCount()+num);
        int i = userMapper.updateByPrimaryKeySelective(user1);
        if (i<1){
            throw new Exception();
        }
        User user2 = new User();
        user2.setId(phoneUser.getId());
        user2.setAllCount(phoneUser.getAllCount()+num);
        int b = userMapper.updateByPrimaryKeySelective(user2);
        if (b<1){
            throw new Exception();
        }
    }
}
