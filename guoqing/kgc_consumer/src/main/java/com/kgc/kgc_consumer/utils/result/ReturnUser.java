package com.kgc.kgc_consumer.utils.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/7 9:53
 * @since
 */
@Data
public class ReturnUser implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private Date createTime;
    private Date updateTime;
    private Integer userLevel;
    private Integer isDelete;
    private String phone;
}
