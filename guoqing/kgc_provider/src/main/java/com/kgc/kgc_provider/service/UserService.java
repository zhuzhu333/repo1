package com.kgc.kgc_provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.kgc_provider.model.User;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/5 15:38
 * @since
 */

public interface UserService {
  public int register(User user);
  public User login(String phone,String password);
  public int del(String phone);
  List<User> getAllUser(User user);
}
