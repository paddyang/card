package com.card.constant;

/**
 * @author: yangPan
 * @date: 2019/10/27 15:04
 * @description:
 */
public class RedisKey {

    /**
     * 所有卡密类型
     */
    public static final String CARD_TYPE="card_type";

    /**
     * 卡密信息
     */
    public static final String CARD_INFO="card_info:";

    /**
     * 卡密信息过期时间
     */
    public static final int CARD_INFO_EXPIRE=60*60*24;

    /**
     * ip过期时间
     */
    public static final int IP_CHECK_EXPIRE=10;

    /**
     * 验证失败限定的key
     */
    public static final String IP_CHECK="ip_address:";

    public static final String LOGIN_CHECK="login_check:";

    /**
     * 验证日志
     */
    public static final String LOG_CHECK="log_check";
}
