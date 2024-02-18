package com.leiyuan.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.leiyuan.common.R;
import com.leiyuan.entity.User;
import com.leiyuan.mapper.UserMapper;
import com.leiyuan.service.UserService;
import com.leiyuan.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //获取验证码
    @Override
    public String getCode(String mail) {
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        // 设置标题
        String subject = "欢迎登录外卖点餐平台！";
        // 设置正文
        String content = "尊敬的用户您好，您正在登录外卖点餐平台，您的验证码为" + code + "！" +
                "验证码五分钟内有效，请确保是本人操作，不要将验证码泄露给其他人!";
        try {
            log.info("mail参数{}; subject{}; content{}", mail , subject , content);
            MailUtil.send(mail, subject, content, false);
            log.info("发送完成！目标邮件地址：{}" , mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    //查询用户是否存在
    @Override
    public User getUser(String mail) {
        return userMapper.selectUserByMail(mail);
    }

    @Override
    public R addUser(User user) {
        int i = userMapper.addUser(user);
        if (i > 0){
            return new R("1","注册成功",null);
        }
        return new R("401","网络异常",null);
    }
}
