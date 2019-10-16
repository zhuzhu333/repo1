package com.kgc.kgc_consumer.contants.wx;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/9 10:34
 * @since
 */
@Component
@ConfigurationProperties(prefix = "wx")
public class WxLoginUri {
    private String appid;
    private String redirectUri;
    private String codeUrl;
    private String appSecret;
    private String accessTokenUrl;
    private String userInfoUrl;

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String reqCodeUrl(){
        StringBuffer buffer=new StringBuffer("redirect:");
        buffer.append(codeUrl).append("appid=").append(appid).append("&").append("redirect_uri=").append(redirectUri).append("&").
                append("response_type=").append("code").append("&").append("scope=").append("snsapi_userinfo").append("&").append("state=").append("STATE#wechat_redirect");
        return buffer.toString();
    }
    public String reqAccessTokenUrl(String code){
        StringBuffer buffer=new StringBuffer(accessTokenUrl);
        buffer.append("appid=").append(appid).append("&").append("secret=").append(appSecret).append("&").
                append("code=").append(code).append("&").append("grant_type=authorization_code");
        return buffer.toString();
    }
    public String reqUserInfoUrl(String accessToken,String openId){
        StringBuffer buffer=new StringBuffer(userInfoUrl);
        buffer.append("access_token=").append(accessToken).append("&").append("openid=").append(openId);
        return buffer.toString();
    }
}
