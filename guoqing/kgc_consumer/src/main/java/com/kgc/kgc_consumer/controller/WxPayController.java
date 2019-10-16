package com.kgc.kgc_consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.kgc.kgc_consumer.contants.wx.WxPayUri;

import com.kgc.kgc_consumer.service.api.WxPayService;
import com.kgc.kgc_consumer.utils.WxPayUtils;
import com.kgc.kgc_provider.model.Goods;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/15 13:51
 * @since
 */
@Api(value = "微信支付")
@RestController
@RequestMapping(value = "/wxPay")
public class WxPayController {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private WxPayUri wxPayUri;


    @ApiOperation(value = "同意下单")
    @GetMapping(value = "/pay")
    public String wxPay(@Valid Goods goods) throws Exception{
    /*    SortedMap<String, String> param = new TreeMap<String, String>();
        param.put("appid",wxPayUri.getAppid());
        param.put("mch_id",wxPayUri.getMchid());
        param.put("nonce_str",CommonUtil.createUUID(32));
        param.put("body",goods.getGoodName());
        param.put("out_trade_no",CommonUtil.createUUID(16));
        param.put("total_fee",goods.getPrice());
        param.put("spbill_create_ip","192.168.1.187");
        param.put("notify_url",wxPayUri.getNotifyurl());
        param.put("trade_type",wxPayUri.getType());
        String sign=WxPayUtils.generateSignature(param,wxPayUri.getKey());
        param.put("sign", sign);
        //将map转成xml
        String WxXmlstr=WxPayUtils.mapToXml(param);
        String WxreturnXml= UrlUtils.doPost(wxPayUri.getUnified(),WxXmlstr,5000);
        Map<String,String> WxReturnMap=WxPayUtils.xmlToMap(WxreturnXml);*/

   return wxPayService.wxPay(goods);


    }

    @RequestMapping(value = "/wxPayNotify")
    public void wxPayNotify(HttpServletRequest request , HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(   inputStream, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        Map<String, String> resultMap = WxPayUtils.xmlToMap(sb.toString());
        //成功回调了
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            //验证签名与金额
            boolean isCheckSign = WxPayUtils.checkSign(resultMap,wxPayUri.getKey());
            if (isCheckSign) {
                //todo
                //xxxx();
                Map<String, String> rMap = Maps.newHashMap();
                rMap.put("return_code", "SUCCESS");
                rMap.put("return_msg", "OK");
                String xml = WxPayUtils.mapToXml(rMap);
                response.getWriter().write(xml);
            }
        }
    }
}
