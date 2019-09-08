package com.card.controller;

import com.card.constant.TypeEnum;
import com.card.pojo.Card;
import com.card.pojo.CardInfo;
import com.card.pojo.User;
import com.card.service.CardsService;
import com.card.utils.MD5Utils;
import com.card.service.UserService;
import com.card.utils.BaseResponse;
import com.card.utils.GsonUtils;
import com.card.utils.MobileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/7/1 22:46
 * @description:
 */

@RestController
public class UserController {

    @Autowired
    private CardsService cardsService;

    @Autowired
    private UserService userService;


    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",produces="text/json;charset=UTF-8")
    public String login(String username, String password, HttpSession session){
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return GsonUtils.GsonString(BaseResponse.build(400,"用户名或者密码不能为空！"));
        }
        User user = userService.getByUsername(username);
        if (null==user){
            return GsonUtils.GsonString(BaseResponse.build(400,"用户名或者密码错误！"));
        }
        String newPass = MD5Utils.md5(password);
        if (newPass.equals(user.getPassword())){
            session.setAttribute("username",user.getUsername());
            return GsonUtils.GsonString(BaseResponse.success());
        }
        return GsonUtils.GsonString(BaseResponse.build(400,"用户名或者密码错误！"));
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout",produces="text/json;charset=UTF-8")
    public String logout(HttpSession session){
        session.invalidate();
        return GsonUtils.GsonString(BaseResponse.build(200,"退出成功！"));
    }

    /**
     * 获取卡密数量信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/getUserInfo",produces="text/json;charset=UTF-8")
    public String getUserInfo(HttpSession session){
        try {
            String username = (String) session.getAttribute("username");
            if (StringUtils.isBlank(username)){
                return GsonUtils.GsonString(BaseResponse.build(400,"登录超时！"));
            }
            User user = userService.getByUsername(username);
            CardInfo info=new CardInfo();
            info.setUsername(user.getUsername());
            info.setAllCard(user.getAllCount());
            info.setUsedCard(user.getNowCount());
            info.setNotUsedCard(cardsService.getNoUsedCardNum(user.getId()));
            info.setRemainCard(user.getAllCount()-user.getNowCount());
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sf.format(new Date());
            info.setDateStr("时间："+dateStr);
            return GsonUtils.GsonString(BaseResponse.success(info));
        } catch (Exception e) {
            return GsonUtils.GsonString(BaseResponse.build(400,"请重新登录！"));
        }
    }

    /**
     * 修改密码
     * @param session
     * @param oldPass
     * @param newPass
     * @param newPass2
     * @return
     */
    @RequestMapping(value = "/editPass",produces="text/json;charset=UTF-8")
    public String editPass(HttpSession session,String oldPass,String newPass,String newPass2){
        if (StringUtils.isBlank(oldPass)||StringUtils.isBlank(newPass)||StringUtils.isBlank(newPass2)){
            return GsonUtils.GsonString(BaseResponse.build(400,"输入密码不能为空！"));
        }
        if (!newPass.equals(newPass2)){
            return GsonUtils.GsonString(BaseResponse.build(400,"两次密码不一致！"));
        }
        String username = (String) session.getAttribute("username");
        User oldUser = userService.getByUsername(username);
        if (!oldUser.getPassword().equals(MD5Utils.md5(oldPass))){
            return GsonUtils.GsonString(BaseResponse.build(400,"原密码错误！"));
        }
        userService.editPass(oldUser.getId(),newPass);
        return GsonUtils.GsonString(BaseResponse.success());
    }

    /**
     * 获取代理
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getAgentInfo",produces="text/json;charset=UTF-8")
    public String getAgentInfo(HttpSession session,Integer pageNum,Integer pageSize){
        if (null==pageNum||0>pageNum){
            pageNum=1;
        }
        if (null==pageSize||0>pageSize){
            pageSize=15;
        }
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        PageHelper.startPage(pageNum, pageSize);
        User query = new User();
        query.setParentId(user.getId());
        List<User> list=userService.getByQuery(query);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return GsonUtils.GsonString(BaseResponse.success(pageInfo));
    }

    /**
     * 添加代理
     * @param session
     * @param phone
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/addAgent",produces="text/json;charset=UTF-8")
    public String addAgent(HttpSession session,String phone,String nickname,String type){
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(nickname)){
            return GsonUtils.GsonString(BaseResponse.build(200,"手机号或昵称不能为空！"));
        }
        if(phone.length() != 11 || !MobileUtil.isMobile(phone)){
            return GsonUtils.GsonString(BaseResponse.build(200,"请输入正确的手机号！"));
        }
        User user = userService.getByUsername(phone);
        if (null != user){
            return GsonUtils.GsonString(BaseResponse.build(200,"手机号已被注册！"));
        }
        String loginUsername = (String) session.getAttribute("username");
        User loginUser = userService.getByUsername(loginUsername);
        user = new User();
        user.setParentId(loginUser.getId());
        user.setUsername(phone);
        user.setNickname(nickname);
        if (loginUser.getType().equals(TypeEnum.ALL.getCode())){
            user.setType(type);
        }else{
            user.setType(loginUser.getType());
        }
        user.setLevel(loginUser.getLevel()+1);
        userService.addUser(user);
        return GsonUtils.GsonString(BaseResponse.build(200,"添加成功!"));
    }

    /**
     * 转增授权
     * @param session
     * @param phone
     * @param num
     * @return
     */
    @RequestMapping(value = "/change",produces="text/json;charset=UTF-8")
    public String change(HttpSession session,String phone,Integer num){
        try {
            if (null==num || num<0){
                return GsonUtils.GsonString(BaseResponse.build(200,"请输入正确的转增数量!"));
            }
            User phoneUser = userService.getByUsername(phone);
            if (null==phoneUser){
                return GsonUtils.GsonString(BaseResponse.build(200,"用户不存在!"));
            }
            if (phoneUser.getAllCount()<num){
                return GsonUtils.GsonString(BaseResponse.build(200,"可用授权书数量不足!"));
            }
            String username = (String) session.getAttribute("username");
            User user = userService.getByUsername(username);
            if (!user.getType().equals(phoneUser.getType())){
                return GsonUtils.GsonString(BaseResponse.build(200,"用户类型不匹配!"));
            }
            //转增
             userService.change(user,num,phoneUser);
            return GsonUtils.GsonString(BaseResponse.build(200,"转赠成功！"));
        } catch (Exception e) {
            return GsonUtils.GsonString(BaseResponse.build(200,"转赠失败！"));
        }
    }
}
