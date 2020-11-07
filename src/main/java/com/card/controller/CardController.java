package com.card.controller;

import com.card.constant.CardStatusEnum;
import com.card.constant.RedisKey;
import com.card.constant.StatusEnum;
import com.card.constant.TypeEnum;
import com.card.pojo.Card;
import com.card.pojo.CardType;
import com.card.pojo.User;
import com.card.service.CardTypeService;
import com.card.service.CardsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.card.service.UserService;
import com.card.utils.BaseResponse;
import com.card.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangPan
 * @date: 2019/7/6 19:32
 * @description:
 */
@RestController
public class CardController {

    @Autowired
    private CardsService cardsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${super.user}")
    private String superUser;

    @GetMapping(value = "/testConfig", produces="text/json;charset=UTF-8")
    public String testConfig(){
        return superUser;
    }

    /**
     * 新增卡密
     * @param session
     * @param num
     * @param days
     * @return
     */
    @PostMapping(value = "/add", produces="text/json;charset=UTF-8")
    public String addCard(HttpSession session,Integer num,Integer days,String safeCode){
        //数量不能为null或者负数
        if (null==num||num<0){
            num=1;
        }
        if (null==days||365<days||days<0){
            days=365;
        }
        String username = (String) session.getAttribute("username");
        if (StringUtils.isBlank(username)){
            return GsonUtils.GsonString(BaseResponse.build(400,"登录超时，请重新登录！"));
        }
        User user = userService.getByUsername(username);
        int remainCardNum = user.getAvailableNum();
        if (remainCardNum<num){
            return GsonUtils.GsonString(BaseResponse.build(400,"可生产卡密数量不足！"));
        }
        if (user.getType().equals(TypeEnum.ALL.getCode())){
            cardsService.batchAddCard(user.getId(),num,days,safeCode);
        }else{
            cardsService.batchAddCard(user.getId(),num,days,user.getType());
        }
        userService.updateUsedNum(user.getId(),user.getAvailableNum()-num,user.getUsedNum()+num);
        return GsonUtils.GsonString(BaseResponse.success());
    }

    /**
     * 查用户已激活的卡密
     * @return
     */
    @RequestMapping(value = "/getActiveCard",produces="text/json;charset=UTF-8")
    public String getActiveCard(HttpSession session,Integer pageNum,Integer pageSize){
        if (null==pageNum||0>pageNum){
            pageNum=1;
        }
        if (null==pageSize||0>pageSize){
            pageSize=15;
        }
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        PageHelper.startPage(pageNum, pageSize);
        List<Card> list=cardsService.getActiveCard(user.getId());
        PageInfo<Card> pageInfo = new PageInfo<>(list);
        return GsonUtils.GsonString(BaseResponse.success(pageInfo));
    }

    /**
     * 查用户已锁定卡密
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getLockCard",produces="text/json;charset=UTF-8")
    public String getLockCard(HttpSession session,Integer pageNum,Integer pageSize){
        if (null==pageNum||0>pageNum){
            pageNum=1;
        }
        if (null==pageSize||0>pageSize){
            pageSize=15;
        }
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        PageHelper.startPage(pageNum, pageSize);
        List<Card> list=cardsService.getLockCard(user.getId());
        PageInfo<Card> pageInfo = new PageInfo<>(list);
        return GsonUtils.GsonString(BaseResponse.success(pageInfo));
    }

    /**
     * 锁卡
     * @param cardId
     * @param session
     * @return
     */
    @RequestMapping(value = "/lockOrUnlock",produces="text/json;charset=UTF-8")
    public String lock(Integer cardId,HttpSession session){
        String username = (String) session.getAttribute("username");
        Card card=cardsService.getByCardId(cardId);
        User user = userService.getByUsername(username);
        if (user.getId().intValue()==card.getUserId().intValue()){
            if (card.getStatus()==CardStatusEnum.ACTIVATED.getCode()){
                cardsService.lock(cardId);
            }
            if (card.getStatus()==CardStatusEnum.LOCKED.getCode()){
                cardsService.unLock(cardId);
            }
        }
        return GsonUtils.GsonString(BaseResponse.success(card));
    }

    /**
     * 锁卡
     * @param cardNo
     * @param session
     * @return
     */
    @RequestMapping(value = "/lockCard",produces="text/json;charset=UTF-8")
    public String lock(String cardNo,HttpSession session){
        String username = (String) session.getAttribute("username");
        Card card=cardsService.getByCardNo(cardNo);
        User user = userService.getByUsername(username);
        if (user.getId().intValue()==card.getUserId().intValue()){
            cardsService.lock(card.getId());
            return GsonUtils.GsonString(BaseResponse.success(card));
        }
        return GsonUtils.GsonString(BaseResponse.build(200,"失败"));
    }

    /**
     * 获取未用卡密
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getNotUsedCards",produces="text/json;charset=UTF-8")
    public String getNotUsedCards(HttpSession session,Integer pageNum,Integer pageSize){
        if (null==pageNum||0>pageNum){
            pageNum=1;
        }
        if (null==pageSize||0>pageSize){
            pageSize=10;
        }
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        PageHelper.startPage(pageNum,pageSize);
        List<Card> list=cardsService.getNotUsedCards(user.getId());
        PageInfo<Card> pageInfo = new PageInfo<>(list);
        return GsonUtils.GsonString(BaseResponse.success(pageInfo));
    }

    /**
     * 获取用户的卡密类型
     * @return
     */
    @RequestMapping(value = "/getCardType",produces="text/json;charset=UTF-8")
    public String getCardType(HttpSession session){
        List<CardType> list=cardTypeService.getAllType(StatusEnum.VALID.getCode());
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        //超级管理员
        if (user.getType().equalsIgnoreCase(TypeEnum.ALL.getCode())){
            return GsonUtils.GsonString(BaseResponse.success(list));
        }

        List<CardType> types = new ArrayList<>();
        for (CardType cardType:list) {
            if (user.getType().contains(cardType.getCardType())){
                types.add(cardType);
            }
        }
        return GsonUtils.GsonString(BaseResponse.success(types));
    }

    /**
     * 获取所有卡密类型
     * @return
     */
    @RequestMapping(value = "/getAllCardType",produces="text/json;charset=UTF-8")
    public String getAllCardType(HttpSession session){
        List<CardType> list=cardTypeService.getAllType(StatusEnum.VALID.getCode());
        return GsonUtils.GsonString(BaseResponse.success(list));
    }

    /**
     * 新增卡密类型
     * @param cardType
     * @param name
     * @return
     */
    @RequestMapping(value = "/addCardType",produces="text/json;charset=UTF-8")
    public String addCardType(HttpSession session,String cardType, String name){
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        if (!user.getType().equalsIgnoreCase("ALL")){
            return GsonUtils.GsonString(BaseResponse.build(400,"您没有权限！"));
        }
        cardTypeService.addCardType(cardType.toUpperCase(),name);
        //清除redis缓存
        redisTemplate.delete(RedisKey.CARD_TYPE);
        return GsonUtils.GsonString(BaseResponse.success());
    }

    /**
     * 删除卡密类型
     * @param cardType
     * @param name
     * @return
     */
    @RequestMapping(value = "/deleteCardType",produces="text/json;charset=UTF-8")
    public String deleteCardType(HttpSession session,String cardType, String name){
        String username = (String) session.getAttribute("username");
        User user = userService.getByUsername(username);
        if (!user.getType().equalsIgnoreCase(TypeEnum.ALL.getCode())){
            return GsonUtils.GsonString(BaseResponse.build(400,"您没有权限！"));
        }
        //判断卡密是否存在
        CardType byCardType = cardTypeService.getByCardType(cardType);
        if (null == byCardType){
            return GsonUtils.GsonString(BaseResponse.build(400,"您没有权限！"));
        }
        cardTypeService.updateById(byCardType.getId());
        //清除redis缓存
        redisTemplate.delete(RedisKey.CARD_TYPE);
        return GsonUtils.GsonString(BaseResponse.success());
    }

    /**
     * 解绑卡密
     * @param cardNo
     * @param cardType
     * @return
     */
    @RequestMapping(value = "/updateUid",produces="text/json;charset=UTF-8")
    public String updateUid(String cardNo,String cardType){
        if (StringUtils.isBlank(cardNo) || StringUtils.isBlank(cardType)){
            return GsonUtils.GsonString(BaseResponse.build(400,"卡密不能为空"));
        }
        Card card = cardsService.getByCardNoAndType(cardNo, cardType);
        if (null == card){
            return GsonUtils.GsonString(BaseResponse.build(400,"卡密或类型错误"));
        }
        cardsService.updateUid(card.getId(),"unbind");
        //清除redis缓存
        redisTemplate.delete(RedisKey.CARD_INFO+cardNo);
        return GsonUtils.GsonString(BaseResponse.success());
    }


}
