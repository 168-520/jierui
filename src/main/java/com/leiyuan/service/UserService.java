package com.leiyuan.service;

import com.leiyuan.common.R;
import com.leiyuan.entity.User;

public interface UserService {

    public String getCode(String mail);

    public User getUser(String phone);
    public R addUser(User user);
}
