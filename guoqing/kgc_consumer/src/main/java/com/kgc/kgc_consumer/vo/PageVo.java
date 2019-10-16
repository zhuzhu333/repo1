package com.kgc.kgc_consumer.vo;

import java.io.Serializable;

public class PageVo implements Serializable {
    private static final long serialVersionUID = 7151404202658065436L;
    private Integer pSize;
    private Integer sPage;
    private Integer tSize;

    public Integer getpSize() {
        return pSize;
    }

    public void setpSize(Integer pSize) {
        this.pSize = pSize;
    }

    public Integer getsPage() {


        return sPage;
    }

    public void setsPage(Integer sPage) {
        this.sPage = (sPage - 1) * pSize;
    }

    public Integer gettSize() {
        return tSize;
    }

    public void settSize(Integer tSize) {
        this.tSize = tSize;
    }
}
