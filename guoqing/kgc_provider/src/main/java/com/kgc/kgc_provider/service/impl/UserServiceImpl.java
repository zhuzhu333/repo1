package com.kgc.kgc_provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.kgc_provider.mapper.UserMapper;
import com.kgc.kgc_provider.model.User;
import com.kgc.kgc_provider.model.UserExample;
import com.kgc.kgc_provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/5 15:39
 * @since
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public int register(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User login(String phone, String password) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);
        List<User> users=userMapper.selectByExample(userExample);
        if(!CollectionUtils.isEmpty(users)){
            if(password.equals(users.get(0).getPassword())){
                return users.get(0);
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public int del(String phone) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);
        List<User> users=userMapper.selectByExample(userExample);
        User user=users.get(0);
        user.setIsDelete(1);
       return userMapper.updateByExample(user,userExample);
    }

    @Override
    public List<User> getAllUser(User user) {
        List<User> list=userMapper.getAllUser(user);

        return userMapper.getAllUser(user);
    }
}
