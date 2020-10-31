package com.card.controller;

import com.card.constant.CardStatusEnum;
import com.card.pojo.Card;
import com.card.pojo.DiyResp;
import com.card.pojo.wwj.BindAvtCode;
import com.card.pojo.wwj.CardUser;
import com.card.pojo.wwj.PlayerInfo;
import com.card.service.CardTypeService;
import com.card.service.CardUserService;
import com.card.service.CardsService;
import com.card.service.DiyRespService;
import com.card.utils.BaseResp;
import com.card.utils.EncrypUtil;
import com.card.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/diyResp")
public class DiyRespController {

    private static final Logger logger= LoggerFactory.getLogger(DiyRespController.class);

    @Autowired
    private DiyRespService diyRespService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private CardUserService cardUserService;

    /**
     * diy返回结果
     * @param type
     * @return
     */
    @RequestMapping(value = "/{type}",produces = "application/json;charset=UTF-8")
    public String diyResp(@PathVariable String type, Map<String,Object> req){
        System.out.println(req);
        DiyResp diyResp = diyRespService.getByPath(type);
        return diyResp.getOutput();
    }

    @RequestMapping(value = "/{type1}/{type2}",produces = "application/json;charset=UTF-8")
    public String diy2(@PathVariable String type1, @PathVariable String type2,Map<String,Object> req){
        System.out.println(req);
        DiyResp diyResp = diyRespService.getByPath(type1+"/"+type2);
        return diyResp.getOutput();
    }

    @RequestMapping(value = "/{type1}/{type2}/{type3}",produces = "application/json;charset=UTF-8")
    public String diy3(@PathVariable String type1, @PathVariable String type2, @PathVariable String type3,Map<String,Object> req){
        System.out.println(req);
        DiyResp diyResp = diyRespService.getByPath(type1+"/"+type2+"/"+type3);
        return diyResp.getOutput();
    }

    @RequestMapping(value = "/serverTime")
    public String serverTime(String s){
        logger.info("---serverTime---input---{}",s);
        BaseResp baseResp = null;
        try {
            String aesKey = EncrypUtil.rsaDecrypt(s);
            logger.info("---aesKey---{}",aesKey);
            stringRedisTemplate.opsForValue().set("AES_KEY",aesKey);
            baseResp = BaseResp.success(EncrypUtil.aesEncrypt("ok", aesKey));
        } catch (Exception e) {
            baseResp = BaseResp.build(400,"");
        }
        return GsonUtils.GsonString(baseResp);
    }


    @RequestMapping(value = "/bindAvtcode")
    public String bindAvtCode(String data){
        String cardType="WWJ";
        String aesKey = stringRedisTemplate.opsForValue().get("AES_KEY");
        String decrypt = EncrypUtil.aesDecrypt(data,aesKey);
        logger.info("---bindAvtCode---input---{}",decrypt);
        BindAvtCode bindAvtCode = GsonUtils.GsonToBean(decrypt, BindAvtCode.class);
        //激活操作
        Card wwj = cardsService.getByCardNoAndType(bindAvtCode.getAvtCode(), cardTypeService.getByCardType(cardType).getId().toString());
        if (null==wwj){
            return GsonUtils.GsonString(BaseResp.build(400,"no this code"));
        }

        if (CardStatusEnum.NOT_ACTIVE.getCode()==wwj.getStatus()){
            cardsService.activate(wwj.getId(),bindAvtCode.getPlayerId());
            return GsonUtils.GsonString(BaseResp.success());
        }

        if (CardStatusEnum.ACTIVATED.getCode()==wwj.getStatus()){
            return GsonUtils.GsonString(BaseResp.success());
        }
        return GsonUtils.GsonString(BaseResp.build(400,"code error"));
    }

    @RequestMapping(value = "/login")
    public String login(String data){
        String aesKey = stringRedisTemplate.opsForValue().get("AES_KEY");
        String decrypt = EncrypUtil.aesDecrypt(data,aesKey);
        logger.info("---login---input---{}",decrypt);
        CardUser cardUser = GsonUtils.GsonToBean(decrypt, CardUser.class);
        CardUser user = cardUserService.getCardUser(cardUser.getDeviceId());

        DiyResp wwjpass = diyRespService.getByPath("wwjpass");
        PlayerInfo playerInfo = GsonUtils.GsonToBean(wwjpass.getOutput(), PlayerInfo.class);

        boolean flag=false;
        if (null == user){
            cardUserService.insert(cardUser);
            playerInfo.setPlayerId(cardUser.getId().toString());
            playerInfo.setFeatures("0");
            playerInfo.setAvtCodeStatus(true);
            playerInfo.setExpires("请先激活！");
            playerInfo.setAccount(cardUser.getAccount());
            String output = GsonUtils.GsonString(playerInfo);
            String encrypt = EncrypUtil.aesEncrypt(output, aesKey);
            logger.info("---login---output---{}",output);
            return GsonUtils.GsonString(BaseResp.success(encrypt));
        }
        //登录操作
        Integer id = user.getId();
        Card wwj = cardsService.getByUidAndType(id.toString(), cardTypeService.getByCardType("WWJ").getId().toString());
        if (null==wwj){
            playerInfo.setPlayerId(id.toString());
            playerInfo.setFeatures("0");
            playerInfo.setAvtCodeStatus(true);
            playerInfo.setExpires("请先充值！");
            playerInfo.setAccount(cardUser.getAccount());
            String output = GsonUtils.GsonString(playerInfo);
            String encrypt = EncrypUtil.aesEncrypt(output, aesKey);
            logger.info("---login---output---{}",output);
            return GsonUtils.GsonString(BaseResp.success(encrypt));
        }
        //激活码锁定
        if (CardStatusEnum.LOCKED.getCode()==wwj.getStatus()){
            playerInfo.setFeatures("0");
            playerInfo.setAvtCodeStatus(true);
        }
        //卡密到期时间
        Calendar c = Calendar.getInstance();
        if(null != wwj.getUseTime()){
            c.setTime(wwj.getUseTime());
            c.add(Calendar.DATE, wwj.getDays());
        }
        if (c.getTime().before(new Date())){
            playerInfo.setFeatures("0");
            playerInfo.setAvtCodeStatus(true);
        }
        playerInfo.setPlayerId(id.toString());
        playerInfo.setAccount(cardUser.getAccount());
        String output = GsonUtils.GsonString(playerInfo);
        String encrypt = EncrypUtil.aesEncrypt(output, aesKey);
        logger.info("---login---output---{}",output);
        return GsonUtils.GsonString(BaseResp.success(encrypt));

        /*DiyResp wwjnopass = diyRespService.getByPath("wwjnopass");
        String encrypt = EncrypUtil.aesEncrypt(wwjnopass.getOutput(), aesKey);
        logger.info("---login---output---{}",wwjnopass.getOutput());
        return GsonUtils.GsonString(BaseResp.success(encrypt));*/
    }


}
