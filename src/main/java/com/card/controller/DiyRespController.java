package com.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.card.constant.CardStatusEnum;
import com.card.pojo.Card;
import com.card.pojo.DiyResp;
import com.card.pojo.wwj.BindAvtCode;
import com.card.pojo.wwj.LoginVo;
import com.card.service.CardTypeService;
import com.card.service.CardsService;
import com.card.service.DiyRespService;
import com.card.utils.BaseResp;
import com.card.utils.BaseResponse;
import com.card.utils.EncrypUtil;
import com.card.utils.GsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public String bindAvtCode(HttpServletRequest request,String data){
        String cardType="WWJ";
        String aesKey = stringRedisTemplate.opsForValue().get("AES_KEY");
        String decrypt = EncrypUtil.aesDecrypt(data,aesKey);
        logger.info("---bindAvtCode---input---{}",decrypt);
        BindAvtCode bindAvtCode = GsonUtils.GsonToBean(decrypt, BindAvtCode.class);
        //激活操作
        Card wwj = cardsService.getByCardNoAndType(bindAvtCode.getAvtCode(), cardTypeService.getByCardType(cardType).getId().toString());
        if (null==wwj){
            return GsonUtils.GsonString(BaseResp.build(400,"激活码错误"));
        }

        if (CardStatusEnum.NOT_ACTIVE.getCode()==wwj.getStatus()){
            cardsService.activate(CardStatusEnum.ACTIVATED.getCode(),bindAvtCode.getPlayerId());
            return GsonUtils.GsonString(BaseResp.success("激活成功"));
        }

        if (CardStatusEnum.ACTIVATED.getCode()==wwj.getStatus()){
            return GsonUtils.GsonString(BaseResp.success("激活码已被使用"));
        }
        return GsonUtils.GsonString(BaseResp.build(400,"激活码不可用"));
    }

    @RequestMapping(value = "/login")
    public String login(String data){
        String aesKey = stringRedisTemplate.opsForValue().get("AES_KEY");
        String decrypt = EncrypUtil.aesDecrypt(data,aesKey);
        logger.info("---login---input---{}",decrypt);
        LoginVo loginVo = GsonUtils.GsonToBean(decrypt, LoginVo.class);
        //登录操作
        loginVo.getAccount();
        if (true) {
            DiyResp wwjpass = diyRespService.getByPath("wwjpass");
            String encrypt = EncrypUtil.aesEncrypt(wwjpass.getOutput(), aesKey);
            logger.info("---login---output---{}",wwjpass.getOutput());
            return GsonUtils.GsonString(BaseResp.success(encrypt));
        }
        DiyResp wwjnopass = diyRespService.getByPath("wwjnopass");
        String encrypt = EncrypUtil.aesEncrypt(wwjnopass.getOutput(), aesKey);
        logger.info("---login---output---{}",wwjnopass.getOutput());
        return GsonUtils.GsonString(BaseResp.success(encrypt));
    }


}
