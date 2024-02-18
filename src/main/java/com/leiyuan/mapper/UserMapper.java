package com.leiyuan.mapper;

import com.leiyuan.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User selectUserByMail(String mail);
    public int addUser(User user);
}
