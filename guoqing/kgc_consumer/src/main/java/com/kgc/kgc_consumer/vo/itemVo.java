package com.kgc.kgc_consumer.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/12 10:37
 * @since
 */
public class itemVo implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String purchaseTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        this.purchaseTime = sdf.format(purchaseTime);
    }
}
