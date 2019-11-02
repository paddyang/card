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
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    private StringRedisTemplate stringRedisTemplate;

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
        int remainCardNum = user.getAllCount();
        if (remainCardNum<num){
            return GsonUtils.GsonString(BaseResponse.build(400,"可生产卡密数量不足！"));
        }
        if (user.getType().equals(TypeEnum.ALL.getCode())){
            cardsService.batchAddCard(user.getId(),num,days,safeCode);
        }else{
            cardsService.batchAddCard(user.getId(),num,days,user.getType());
        }
        userService.updateNowCount(user.getId(),user.getNowCount()+num);
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
        }
        return GsonUtils.GsonString(BaseResponse.success(card));
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
     * 获取全部卡密类型
     * @return
     */
    @RequestMapping(value = "/getCardType",produces="text/json;charset=UTF-8")
    public String getNotUsedCards(){
        String cardType = stringRedisTemplate.opsForValue().get(RedisKey.CARD_TYPE);
        if (StringUtils.isBlank(cardType)){
            List<CardType> list=cardTypeService.getAllType(StatusEnum.VALID.getCode());
            cardType = GsonUtils.GsonString(BaseResponse.success(list));
            stringRedisTemplate.opsForValue().set(RedisKey.CARD_TYPE,cardType);
        }
        return cardType;
    }

    /**
     * 新增卡密类型
     * @param cardType
     * @param name
     * @return
     */
    @RequestMapping(value = "/addCardType",produces="text/json;charset=UTF-8")
    public String addCardType(String cardType, String name){
        cardTypeService.addCardType(cardType.toUpperCase(),name);

        //更redis缓存
        List<CardType> allType = cardTypeService.getAllType(StatusEnum.VALID.getCode());
        String cache = GsonUtils.GsonString(BaseResponse.success(allType));
        stringRedisTemplate.opsForValue().set(RedisKey.CARD_TYPE,cache);

        return GsonUtils.GsonString(BaseResponse.success());
    }
}
