package com.kgc.kgc_consumer.config.custom;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.kgc.kgc_consumer.utils.RedisUtils;
import com.kgc.kgc_consumer.vo.AllKindUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/8 8:47
 * @since
 */
public class LoginReqComplete implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            String token = request.getHeader("token");  // 从 http 请求头中取出 token
            String wxtoken = request.getHeader("wxtoken");
            String userToken = StringUtils.isNotEmpty((String) redisUtils.get(token)) ? token : wxtoken;
            if (!StringUtils.isBlank(userToken)) {
                String userTokenStr = (String) redisUtils.get(userToken);
                AllKindUserVo loginUserVo = JSONObject.parseObject(userTokenStr, AllKindUserVo.class);
                loginUserVo.setId(userToken);
                request.setAttribute("userToken", loginUserVo);
            } else {
                throw new RuntimeException("login error");
            }
            return true;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
