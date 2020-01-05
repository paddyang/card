package com.card.controller;

import com.card.constant.CardStatusEnum;
import com.card.constant.RedisKey;
import com.card.constant.ReturnCodeEnum;
import com.card.constant.StatusEnum;
import com.card.pojo.Card;
import com.card.pojo.CardType;
import com.card.pojo.LogCheck;
import com.card.service.CardTypeService;
import com.card.service.CardsService;
import com.card.service.LogCheckService;
import com.card.service.UserService;
import com.card.utils.BaseResponse;
import com.card.utils.GsonUtils;
import com.card.utils.HttpUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: yangPan
 * @date: 2019/7/20 19:27
 * @description:
 */
@RestController
public class CheckController {

    @Autowired
    private CardsService cardsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogCheckService logCheckService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通用验证方法
     *
     * @param type
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/check/{type}", produces = "text/json;charset=UTF-8")
    public String checkCommon(HttpServletRequest request,@PathVariable String type, String a, String b) {
        try {
            //校验卡密12位
            if (a.trim().length() != 12) {
                return ReturnCodeEnum.ERROR.getCode();
            }

            //对验证失败的限定一分钟内只能验证一次
            String ipAddress = HttpUtils.getIpAddress(request);
            Object o = redisTemplate.opsForValue().get(RedisKey.IP_CHECK + ipAddress);
            if (null != o){
                return "操作频繁!";
            }

            CardType cardType = (CardType) redisTemplate.opsForHash().get(RedisKey.CARD_TYPE,type.toUpperCase());
            if (null==cardType) {
                cardType = cardTypeService.getByCardType(type.toUpperCase());
                List<CardType> allType = cardTypeService.getAllType(StatusEnum.VALID.getCode());
                HashMap<String, CardType> map = new HashMap<>();
                for (CardType ct :allType) {
                    map.put(ct.getCardType(),ct);
                }
                redisTemplate.opsForHash().putAll(RedisKey.CARD_TYPE,map);
            }
            Integer id = cardType.getId();
            type = cardType.getCardType();

            Card card = (Card) redisTemplate.opsForValue().get(RedisKey.CARD_INFO + a);
            if (null == card) {
                card = cardsService.getByCardNo(a);
                if (null != card && CardStatusEnum.ACTIVATED.getCode()==card.getStatus()) {
                    redisTemplate.opsForValue().set(RedisKey.CARD_INFO + a, card, RedisKey.CARD_INFO_EXPIRE, TimeUnit.SECONDS);
                }
            }

            //卡密到期时间
            Calendar c = Calendar.getInstance();
            if(null != card.getUseTime()){
                c.setTime(card.getUseTime());
                c.add(Calendar.DATE, card.getDays());
            }

            //记录验证是否通过
            ReturnCodeEnum codeEnum;
            LogCheck logCheck=new LogCheck();


            //验证激活码是否存在
            if (null == card) {
                logCheck.setCheckType("1");
                logCheck.setStatus("2");
                codeEnum=ReturnCodeEnum.E5;
            }else
            //验证激活码类型
            if (!card.getSafeCode().equals(id.toString())) {
                logCheck.setCheckType("1");
                logCheck.setStatus("2");
                codeEnum=ReturnCodeEnum.E4;
            }else
            //看到账户是否封停
            if (card.getIsOk() == 1) {
                logCheck.setCheckType("1");
                logCheck.setStatus("2");
                codeEnum=ReturnCodeEnum.E0;
            }else
            //激活码是否锁定,2锁定
            if (card.getStatus() == 2) {
                logCheck.setCheckType("1");
                logCheck.setStatus("2");
                codeEnum = ReturnCodeEnum.E1;
            }else
            //激活码时候激活0未激活，1激活, 2锁定
            if (card.getStatus() == 0) {
                //激活
                cardsService.activate(card.getId(), b);
                logCheck.setCheckType("1");
                logCheck.setStatus("1");
                codeEnum=ReturnCodeEnum.OK;
            }else
            //校验激活码和机器码是否一致
            if (!card.getUid().equals(b)) {
                logCheck.setCheckType("2");
                logCheck.setStatus("2");
                codeEnum=ReturnCodeEnum.E2;
            }else
            //校验时候过期
            if (c.getTime().before(new Date())) {
                logCheck.setCheckType("2");
                logCheck.setStatus("2");
                codeEnum=ReturnCodeEnum.E3;
            }else {
                logCheck.setCheckType("2");
                logCheck.setStatus("1");
                codeEnum = ReturnCodeEnum.OK;
            }
            //插入一条验证记录
            logCheck.setCard(a);
            logCheck.setCardType(type);
            logCheck.setCreated(new Date());
            logCheck.setMark(codeEnum.getDesc());
            if (null != card.getUid()) {
                logCheck.setUid(card.getUid());
            }
            logCheck.setCheckUid(b);
            //如果验证失败,ip记录redis
            if (!codeEnum.getCode().equals(ReturnCodeEnum.OK.getCode())){
                redisTemplate.opsForValue().set(RedisKey.IP_CHECK+ipAddress,a,10,TimeUnit.SECONDS);
            }
            logCheckService.add(logCheck);
            return codeEnum.getCode();
        } catch (Exception e) {
            return ReturnCodeEnum.E9999.getCode();
        }
    }

    /**
     * 获取验证日志
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getCheckInfo", produces = "text/json;charset=UTF-8")
    public String getCheckInfo(HttpSession session, Integer pageNum, Integer pageSize) {
        if (null == pageNum || 0 > pageNum) {
            pageNum = 1;
        }
        if (null == pageSize || 0 > pageSize) {
            pageSize = 15;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<LogCheck> list = logCheckService.getCheckInfo();
        PageInfo<LogCheck> pageInfo = new PageInfo<>(list);
        return GsonUtils.GsonString(BaseResponse.success(pageInfo));
    }

}