package com.kgc.kgc_consumer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/5 14:27
 * @since
 */
@Data
public class UserVo implements Serializable {
    @ApiModelProperty(value = "名字",required = true,example = "1")
    private String userName;
    @ApiModelProperty(value = "密码",required = true,example = "1")
    private String password;
    @ApiModelProperty(value = "手机号",required = true,example = "1")
    private String phone;
}
