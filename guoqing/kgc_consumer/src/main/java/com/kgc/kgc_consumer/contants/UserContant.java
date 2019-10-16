package com.kgc.kgc_consumer.contants;

import org.springframework.stereotype.Component;

/**
 * Created by boot on 2019/9/28.
 */
@Component
public final class UserContant {
    //用户注册时判断用户名是否重复的namespace
    public static final String REDIS_USER_LOGIN_NAME_SPACE = "USER_REGISTER_NAME:";
    public static final int USER_IS_LOGIN_FAIL_CODE = 250;
    public static final int USER_IS_REGISTER_FAIL_CODE = 350;
    public static final int USER_IS_DEL_FAIL_CODE = 450;
    public static final String USER_BUY="USER_BUY:";
    public static final String BUY_SUCCESS="抢购成功！";
    public static final String BUY_FAIL="抢购失败！";
    public static final String STOCK_NULL="库存不足！";

}
