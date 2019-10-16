package com.kgc.kgc_consumer.service.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.kgc_consumer.contants.wx.WxPayUri;
import com.kgc.kgc_consumer.utils.CommonUtil;
import com.kgc.kgc_consumer.utils.UrlUtils;
import com.kgc.kgc_consumer.utils.WxPayUtils;
import com.kgc.kgc_provider.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/15 17:38
 * @since
 */
@Component
public class WxPayService {
    @Autowired
    private WxPayUri wxPayUri;
    public String wxPay(Goods goods) throws Exception{

        SortedMap<String, String> param = new TreeMap<String, String>();
        param.put("appid",wxPayUri.getAppid());
        param.put("mch_id",wxPayUri.getMchid());
        param.put("nonce_str", CommonUtil.createUUID(32));
        param.put("body",goods.getGoodName());
        param.put("out_trade_no",CommonUtil.createUUID(16));
        param.put("total_fee",goods.getPrice());
        param.put("spbill_create_ip","192.168.1.187");
        param.put("notify_url",wxPayUri.getNotifyurl());
        param.put("trade_type",wxPayUri.getType());
        String sign= WxPayUtils.generateSignature(param,wxPayUri.getKey());
        param.put("sign", sign);
        //将map转成xml
        String WxXmlstr=WxPayUtils.mapToXml(param);
        String WxreturnXml= UrlUtils.doPost(wxPayUri.getUnified(),WxXmlstr,5000);
        Map<String,String> WxReturnMap=WxPayUtils.xmlToMap(WxreturnXml);
        return WxReturnMap.get("code_url");
    }
}
