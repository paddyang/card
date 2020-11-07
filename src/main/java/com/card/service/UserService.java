package com.card.service;

import com.card.pojo.User;

import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/7/3 20:27
 * @description:
 */
public interface UserService {

    User getByUsername(String username);

    void updateUsedNum(int userId,int availableNum,int usedNum);

    void editPass(int userId,String password);

    List<User> getByQuery(User user);

    void addUser(User user);

    void change(User user,int num, User phoneUser) throws Exception;

}
