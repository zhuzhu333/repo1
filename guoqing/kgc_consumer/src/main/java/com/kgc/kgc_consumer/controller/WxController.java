package com.kgc.kgc_consumer.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.kgc.kgc_consumer.config.custom.CurrentUser;
import com.kgc.kgc_consumer.config.custom.LoginRequired;
import com.kgc.kgc_consumer.contants.UserContant;
import com.kgc.kgc_consumer.contants.wx.WxLoginUri;
import com.kgc.kgc_consumer.utils.ActiveMQUtils;
import com.kgc.kgc_consumer.utils.RedisUtils;
import com.kgc.kgc_consumer.utils.UrlUtils;

import com.kgc.kgc_consumer.utils.result.Pages;
import com.kgc.kgc_consumer.utils.result.ReturnResult;
import com.kgc.kgc_consumer.utils.result.ReturnResultUtils;
import com.kgc.kgc_consumer.vo.AllKindUserVo;
import com.kgc.kgc_consumer.vo.PageVo;
import com.kgc.kgc_consumer.vo.itemVo;
import com.kgc.kgc_consumer.wxuser.WxUser;
import com.kgc.kgc_provider.model.ItemKillSuccess;
import com.kgc.kgc_provider.model.WxuSer;
import com.kgc.kgc_provider.model.item;
import com.kgc.kgc_provider.service.ItemService;
import com.kgc.kgc_provider.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/9 10:24
 * @since
 */
@Api(tags = "微信登录")
@Controller
@RequestMapping(value = "/wx")
public class WxController {

    @Autowired
    private WxLoginUri wxLoginUri;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ActiveMQUtils activeMQUtils;
    @Reference
    private ItemService itemService;
    @Reference
    private WxUserService wxUserService;

    @RequestMapping("/send")
    public String sendCode() {
        return wxLoginUri.reqCodeUrl();
    }

    @ResponseBody
    @RequestMapping("/return")
    public String returnCode(String code) {
        //通过code参数加上AppID和AppSecret等拼接url
        String wxLoginUrl = wxLoginUri.reqAccessTokenUrl(code);
        //通过UrlUtils处理url
        String wxReback = UrlUtils.loadURL(wxLoginUrl);
        //处理返回的数据，获取access_token，openid
        JSONObject js = JSONObject.parseObject(wxReback);
        String accessToken = js.getString("access_token");
        String openid = js.getString("openid");
        //通过accessToken加上openid拼接url
        String userInfoUrl = wxLoginUri.reqUserInfoUrl(accessToken, openid);
        //获取用户信息
        String userInfoStr = UrlUtils.loadURL(userInfoUrl);
        //将json字符串用户信息转成实体类
        WxUser wxUser = JSONObject.parseObject(userInfoStr, WxUser.class);
        //把实体类存入数据库
        WxuSer wxuSer = new WxuSer();
        BeanUtils.copyProperties(wxUser, wxuSer);
        boolean isEixt = wxUserService.isExit(wxuSer);
        if (isEixt == false) {
            wxUserService.insert(wxuSer);
        }
        //获取微信号(加密过的)
        String wxId = JSONObject.parseObject(userInfoStr).getString("openid");
        //将用户信息存入redis
        redisUtils.set(wxId, userInfoStr);
        return userInfoStr;
    }

    @ResponseBody
    @LoginRequired
    @ApiOperation("验证是否登录")
    @GetMapping("/isLogin")
    public String isLogin(@CurrentUser AllKindUserVo allKindUserVo) {

        return allKindUserVo.toString();
    }

    @ResponseBody
    @ApiOperation("查询用户")
    @GetMapping("/search")
    public ReturnResult<WxUser> searchWxUsers(@ApiParam(value = "sex", required = false) @RequestParam(value = "sex", required = false) String sex,
                                              @ApiParam(value = "city", required = false) @RequestParam(value = "city", required = false) String city) {
        WxuSer wxuSer = new WxuSer();
        if (null != sex) {
            wxuSer.setSex(sex);
        }
        if (null != city) {
            wxuSer.setCity(city);
        }
        List<WxuSer> wxuSers = wxUserService.getAllUser(wxuSer);
        return ReturnResultUtils.returnSuccess(wxuSers);

    }

    @ResponseBody
    @ApiOperation("秒杀商品")
    @GetMapping("/kill")
    @LoginRequired
    public ReturnResult KillItems(@ApiParam(value = "itemId", required = false) @RequestParam(value = "itemId", required = false) int itemId,
                                  @CurrentUser AllKindUserVo allKindUserVo) {
        boolean islock = redisUtils.lock(String.valueOf(itemId), "1", null);
        while (islock) {
            boolean isExit = itemService.isExist(new Long(itemId)) == 0 ? false : true;
            if (isExit) {
                String userId = allKindUserVo.getId() == null ? allKindUserVo.getOpenid() : allKindUserVo.getId();
                Object result = redisUtils.get(UserContant.USER_BUY + userId + itemId);
                if (null == result) {
                    //（生成订单,减库存）实体类
                    ItemKillSuccess itemKillSuccess = new ItemKillSuccess();
                    itemKillSuccess.setUserId(userId);
                    itemKillSuccess.setItemId(itemId);
                    activeMQUtils.sendQueueMesage("itemKillSuccess", itemKillSuccess);
                    return ReturnResultUtils.returnSuccess(UserContant.BUY_SUCCESS);
                }
                return ReturnResultUtils.returnSuccess(UserContant.BUY_FAIL);
            }
            return ReturnResultUtils.returnSuccess(UserContant.STOCK_NULL);
        }
        return ReturnResultUtils.returnFail(1090, "抢购失败");
    }

    @LoginRequired
    @ApiOperation("展示商品列表")
    @GetMapping("/showItems")
    @ResponseBody
    public ReturnResult<Pages> showItems(@Valid PageVo pageVo) {
        item item1 = new item();
        // BeanUtils.copyProperties(pageVo,item1);
        item1.setsPage(pageVo.getsPage());
        item1.setpSize(pageVo.getpSize());
        List<item> list = itemService.showItems(item1);
        List<itemVo> itemVos = new ArrayList<>();
        list.forEach(obj -> {
            itemVo itemVo = new itemVo();
            BeanUtils.copyProperties(obj, itemVo);
            itemVos.add(itemVo);
        });
        Pages pages = new Pages();
        pages.setCustomPageSize(pageVo.getpSize());
        pages.setPagesize(pageVo.getpSize());
        pages.setCurrentPage(pageVo.getsPage() / pageVo.getpSize() + 1);
        pages.setTotalCount(pageVo.gettSize());
        pages.setCurrList(itemVos);
        return ReturnResultUtils.returnSuccess(pages);

    }

    @JmsListener(destination = "itemKillSuccess")
    public void listener(ItemKillSuccess itemKillSuccess) {
        //生成订单
        itemService.insertKillItem(itemKillSuccess);
        //限制抢购
        redisUtils.set(UserContant.USER_BUY + itemKillSuccess.getUserId() + itemKillSuccess.getItemId(), itemKillSuccess.getItemId());
        //减少库存
        itemService.cutStock(new Long(itemKillSuccess.getItemId()));
        int i = itemKillSuccess.getItemId().intValue();
        redisUtils.del(String.valueOf(i));
    }


}