package com.card.controller;

import com.card.constant.ReturnCodeEnum;
import com.card.constant.TypeEnum;
import com.card.pojo.Card;
import com.card.pojo.CardType;
import com.card.pojo.LogCheck;
import com.card.service.CardTypeService;
import com.card.service.CardsService;
import com.card.service.LogCheckService;
import com.card.service.UserService;
import com.card.utils.BaseResponse;
import com.card.utils.GsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    /**
     * 通用验证方法
     * @param type
     * @param a
     * @param b
     * @return
     */
    @RequestMapping(value = "/check/{type}",produces="text/json;charset=UTF-8")
    public String checkCommon(@PathVariable String type, String a, String b){
        try {
            //校验卡密12位
            if (a.trim().length()!=12){
                return  ReturnCodeEnum.ERROR.getCode();
            }
            CardType cardType = cardTypeService.getByCardType(type.toUpperCase());
            Integer id = cardType.getId();
            type = cardType.getCardType();

            Card card = cardsService.getByCardNo(a);
            //验证激活码是否存在
            if (null==card){
                logCheckService.add(new LogCheck(a,type,"1",new Date(),"2",ReturnCodeEnum.E5.getDesc()));
                return ReturnCodeEnum.E5.getCode();
            }
            //验证激活码类型
            if (!card.getSafeCode().equals(id.toString())){
                logCheckService.add(new LogCheck(a,type,"1",new Date(),"2",ReturnCodeEnum.E4.getDesc()));
                return ReturnCodeEnum.E4.getCode();
            }
            //看到账户是否封停
            if (card.getIsOk()==1){
                logCheckService.add(new LogCheck(a,type,"1",new Date(),"2",ReturnCodeEnum.E0.getDesc()));
                return ReturnCodeEnum.E0.getCode();
            }
            //激活码是否锁定,2锁定
            if (card.getStatus()==2){
                logCheckService.add(new LogCheck(a,type,"1",new Date(),"2",ReturnCodeEnum.E1.getDesc()));
                return  ReturnCodeEnum.E1.getCode();
            }
            //激活码时候激活0未激活，1激活, 2锁定
            if (card.getStatus()==0){
                //激活
                cardsService.activate(card.getId(),b);
                logCheckService.add(new LogCheck(a,type,"1",new Date(),"1",ReturnCodeEnum.OK.getDesc()));
                return ReturnCodeEnum.OK.getCode();
            }
            //校验激活码和机器码是否一致
            if(!card.getUid().equals(b)){
                logCheckService.add(new LogCheck(a,type,"2",new Date(),"2",ReturnCodeEnum.E2.getDesc()));
                return ReturnCodeEnum.E2.getCode();
            }
            //校验时候过期
            Calendar c=Calendar.getInstance();
            c.setTime(card.getUseTime());
            c.add(Calendar.DATE,card.getDays());
            if (c.getTime().before(new Date())){
                logCheckService.add(new LogCheck(a,type,"2",new Date(),"2",ReturnCodeEnum.E3.getDesc()));
                return ReturnCodeEnum.E3.getCode();
            }
            logCheckService.add(new LogCheck(a,type,"2",new Date(),"1",ReturnCodeEnum.OK.getDesc()));
            return ReturnCodeEnum.OK.getCode();
        } catch (Exception e) {
            return ReturnCodeEnum.E9999.getCode();
        }
    }

    /**
     * 获取验证日志
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getCheckInfo",produces="text/json;charset=UTF-8")
    public String getCheckInfo(HttpSession session, Integer pageNum, Integer pageSize){
        if (null==pageNum||0>pageNum){
            pageNum=1;
        }
        if (null==pageSize||0>pageSize){
            pageSize=15;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<LogCheck> list = logCheckService.getCheckInfo();
        PageInfo<LogCheck> pageInfo = new PageInfo<>(list);
        return GsonUtils.GsonString(BaseResponse.success(pageInfo));
    }
}
