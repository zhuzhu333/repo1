package com.kgc.kgc_provider.service;

import com.kgc.kgc_provider.model.User;
import com.kgc.kgc_provider.model.WxuSer;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/10 11:28
 * @since
 */
public interface WxUserService {
    public int insert(WxuSer wxuSer);
    public boolean isExit(WxuSer wxuSer);
    public List<WxuSer> getAllUser(WxuSer wxuSer);
}
