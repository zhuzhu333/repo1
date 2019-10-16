package com.kgc.kgc_consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.kgc.kgc_consumer.config.custom.LoginRequired;
import com.kgc.kgc_consumer.contants.UserContant;
import com.kgc.kgc_consumer.utils.RedisUtils;
import com.kgc.kgc_consumer.utils.result.Pages;
import com.kgc.kgc_consumer.utils.result.ReturnResult;
import com.kgc.kgc_consumer.utils.result.ReturnResultUtils;
import com.kgc.kgc_consumer.utils.result.ReturnUser;
import com.kgc.kgc_consumer.vo.PageVo;
import com.kgc.kgc_consumer.vo.UserVo;
import com.kgc.kgc_provider.model.User;
import com.kgc.kgc_provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 22:29
 * @since
 */
@Api(tags = "用户操作")
@RestController
@RequestMapping(value = "/test")
public class UserController {
    @Autowired
    private RedisUtils redisUtils;
    @Reference
    private UserService userService;

    /****************** 功能开启********************************************/
    @ApiOperation("注册")
    @GetMapping(value = "/register")
    public ReturnResult Register(@Valid UserVo userVo) {
        //判断redis中是否存在
        boolean isExit = null == redisUtils.get(UserContant.REDIS_USER_LOGIN_NAME_SPACE + userVo.getUserName()) ? false : true;
        if (!isExit) {
            User user = new User();
            user.setUserName(userVo.getUserName());
            user.setPassword(userVo.getPassword());
            user.setPhone(userVo.getPhone());
            Date date = new Date();
            user.setCreateTime(date);
            user.setIsDelete(0);
            user.setUpdateTime(date);
            user.setUserLevel(1);

            try {
                redisUtils.set(UserContant.REDIS_USER_LOGIN_NAME_SPACE + userVo.getUserName(), 1);
                userService.register(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ReturnResultUtils.returnSuccess();
        }
        return ReturnResultUtils.returnFail(UserContant.USER_IS_REGISTER_FAIL_CODE, "注册失败");
    }

    @ApiOperation("登录")
    @GetMapping(value = "/login")
    public ReturnResult Login(@ApiParam(value = "手机号", required = true) @RequestParam(value = "phone", required = true) String phone,
                              @ApiParam(value = "密码", required = true) @RequestParam(value = "password", required = true) String password,
                              HttpServletRequest request) {

        //生成TOKEN
        String token = request.getSession().getId();
        //验证登录
        User user = userService.login(phone, password);
        if (null != user) {
            String str = JSONObject.toJSONString(user);
            redisUtils.set(token, str);
            request.getSession().setAttribute("token", token);
            /*redisUtils.expire(token,60);*/
            return ReturnResultUtils.returnSuccess();
        }
        return ReturnResultUtils.returnFail(UserContant.USER_IS_LOGIN_FAIL_CODE, "登录失败！");

    }

    @ApiOperation("逻辑删除")
    @GetMapping(value = "/del")
    public ReturnResult Del(@ApiParam(value = "手机号", required = true) @RequestParam(value = "phone", required = true) String phone) {
        int a = userService.del(phone);
        if (1 == a) {
            return ReturnResultUtils.returnSuccess();
        }
        return ReturnResultUtils.returnFail(UserContant.USER_IS_DEL_FAIL_CODE, "删除失败");
    }

    @ApiOperation("查询用户列表")
    @LoginRequired
    @GetMapping(value = "/searchUsers")
    public ReturnResult<Pages> SearchUsers(@Valid PageVo pageVo
    ) {

        User user = new User();
        user.setpSize(pageVo.getpSize());
        user.setsPage(pageVo.getsPage());

        List<User> allUser = userService.getAllUser(user);
        List<ReturnUser> returnUsers = new ArrayList<>();

        allUser.forEach(obj -> {
            ReturnUser returnUser = new ReturnUser();
            BeanUtils.copyProperties(obj, returnUser);
            returnUsers.add(returnUser);
        });

        Pages pages = new Pages();
        pages.setPagesize(pageVo.getpSize());
        pages.setCurrentPage(pageVo.getsPage() / pageVo.getpSize() + 1);
        pages.setTotalCount(pageVo.gettSize());
        pages.setCurrList(returnUsers);
        return ReturnResultUtils.returnSuccess(pages);


    }
}
