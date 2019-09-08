package com.card.controller;

import com.card.constant.ReturnCodeEnum;
import com.card.constant.TypeEnum;
import com.card.pojo.Card;
import com.card.service.CardsService;
import com.card.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

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

    @RequestMapping(value = "/check/blzs",produces="text/json;charset=UTF-8")
    public String checkBLZS(String a,String b){
        try {
            //校验卡密12位
            if (a.trim().length()!=12){
                return  ReturnCodeEnum.ERROR.getCode();
            }
            return commonCheck(a,b, TypeEnum.BLZS);
        } catch (Exception e) {
            return ReturnCodeEnum.E9999.getCode();
        }
    }

    /**
     * 防封时代验证FFSD
     * @param a 激活码
     * @param b 机器码
     * @return
     */
    @RequestMapping(value = "/check/ffsd",produces="text/json;charset=UTF-8")
    public String checkFFSD(String a,String b){
        try {
            //校验卡密12位
            if (a.trim().length()!=12){
                return  ReturnCodeEnum.ERROR.getCode();
            }
            return commonCheck(a,b, TypeEnum.FFSD);
        } catch (Exception e) {
            return ReturnCodeEnum.E9999.getCode();
        }
    }

    /**
     * 安卓春天验证AZCT
     * @param a 激活码
     * @param b 机器码
     * @return
     */
    @RequestMapping(value = "/check/azct",produces="text/json;charset=UTF-8")
    public String checkAZCT(String a,String b){
        try {
            //校验卡密12位
            if (a.trim().length()!=12){
                return  ReturnCodeEnum.ERROR.getCode();
            }

            return commonCheck(a,b,TypeEnum.AZCT);
        } catch (Exception e) {
            return ReturnCodeEnum.E9999.getCode();
        }
    }

    /**
     * 随心验证SX
     * @param a 激活码
     * @param b 机器码
     * @return
     */
    @RequestMapping(value = "/check/sx",produces="text/json;charset=UTF-8")
    public String checkSX(String a,String b){
        try {
            //校验卡密12位
            if (a.trim().length()!=12){
                return  ReturnCodeEnum.ERROR.getCode();
            }
            return commonCheck(a,b,TypeEnum.SX);
        } catch (Exception e) {
            return ReturnCodeEnum.E9999.getCode();
        }
    }

    /**
     * 通用验证方法
     * @param b 机器码
     * @param card
     * @return
     */
    /**
     *
     * @param cardNo 激活码
     * @param uid 机器码
     * @param typeEnum
     * @return
     */
    private String commonCheck(String cardNo,String uid, TypeEnum typeEnum) {
        Card card = cardsService.getByCardNo(cardNo);
        //验证激活码是否存在
        if (null==card){
            return ReturnCodeEnum.E5.getCode();
        }
        //验证激活码类型AZCT
        if (!card.getSafeCode().equals(typeEnum.getCode())){
            return ReturnCodeEnum.E4.getCode();
        }
        //看到账户是否封停
        if (card.getIsOk()==1){
            return ReturnCodeEnum.E0.getCode();
        }
        //激活码是否锁定,2锁定
        if (card.getStatus()==2){
            return  ReturnCodeEnum.E1.getCode();
        }
        //激活码时候激活0未激活，1激活, 2锁定
        if (card.getStatus()==0){
            //激活
            cardsService.activate(card.getId(),uid);
            return ReturnCodeEnum.OK.getCode();
        }
        //校验激活码和机器码是否一致
        if(!card.getUid().equals(uid)){
            return ReturnCodeEnum.E2.getCode();
        }
        //校验时候过期
        Calendar c=Calendar.getInstance();
        c.setTime(card.getUseTime());
        c.add(Calendar.DATE,card.getDays());
        if (c.getTime().before(new Date())){
            return ReturnCodeEnum.E3.getCode();
        }
        return ReturnCodeEnum.OK.getCode();
    }
}
