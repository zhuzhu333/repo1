package com.kgc.kgc_consumer.config.custom;


import com.kgc.kgc_consumer.vo.AllKindUserVo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by boot on 2019/9/29.
 */
public class CurrentComplete implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(AllKindUserVo.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        AllKindUserVo loginUserVo = (AllKindUserVo) webRequest.getAttribute("userToken", RequestAttributes.SCOPE_REQUEST);
        if (loginUserVo != null) {
            return loginUserVo;
        }
        return null;
    }
}
