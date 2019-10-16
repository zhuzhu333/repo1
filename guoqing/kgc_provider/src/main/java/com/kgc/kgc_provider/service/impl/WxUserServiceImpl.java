package com.kgc.kgc_provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.kgc_provider.mapper.WxuSerMapper;
import com.kgc.kgc_provider.model.User;
import com.kgc.kgc_provider.model.WxuSer;
import com.kgc.kgc_provider.model.WxuSerExample;
import com.kgc.kgc_provider.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/10 11:30
 * @since
 */
@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    private WxuSerMapper wxuSerMapper;
    @Override
    public int insert(WxuSer wxuSer) {

        return wxuSerMapper.insert(wxuSer);
    }

    @Override
    public boolean isExit(WxuSer wxuSer) {
        WxuSerExample wxuSerExample=new WxuSerExample();
        wxuSerExample.createCriteria().andOpenidEqualTo(wxuSer.getOpenid());
        List<WxuSer> users=wxuSerMapper.selectByExample(wxuSerExample);

        if(users.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public List<WxuSer> getAllUser(WxuSer wxuSer) {
        List<WxuSer> list=wxuSerMapper.getAllUser(wxuSer);
        return  list;
    }
}
