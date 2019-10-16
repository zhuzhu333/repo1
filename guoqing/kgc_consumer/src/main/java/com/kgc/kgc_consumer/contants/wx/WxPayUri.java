package com.kgc.kgc_consumer.contants.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/15 13:47
 * @since
 */
@Component
@ConfigurationProperties(prefix = "wxPayConfig")
@Data
public class WxPayUri {
    private String appid;
    private String mchid;
    private String unified;
    private String type;
    private String key;
    private String notifyurl;
}
