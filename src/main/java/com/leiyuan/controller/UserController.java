package com.leiyuan.controller;

import com.leiyuan.common.R;
import com.leiyuan.entity.User;
import com.leiyuan.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user){
        //获取邮箱
        String mail = user.getMail();

        if(mail == null || mail == ""){
            return new R("401","邮箱不能为空",null);
        }

        //调用阿里云提供的短信服务API完成发送短信
        //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

        //需要将生成的验证码保存到Session
//        session.setAttribute(phone,code);


        String code = userService.getCode(mail);

        redisTemplate.opsForValue().set("mail:"+mail,code,5L, TimeUnit.MINUTES);
        return new R("1","发送成功",code);
    }


    @PostMapping("/login")
    public R login(@RequestBody Map map){
        log.info(map.toString());

        //获取手机号
//        String phone = map.get("phone").toString();

        //获取邮箱
        String mail = map.get("mail").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从Redis中获取保存的验证码
        String codeInRedis = (String) redisTemplate.opsForValue().get("mail:"+mail);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInRedis != null && codeInRedis.equals(code)){
            //如果能够比对成功，说明登录成功

            User user = userService.getUser(mail);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setMail(mail);
                user.setStatus(1);
                userService.addUser(user);
            }
            JwtBuilder builder = Jwts.builder();
            HashMap<String,Object> userMap = new HashMap<>();
            userMap.put("mail",user.getMail());
            String token = builder.setSubject(code)                 //主题，就是token中携带的数据
                    .setIssuedAt(new Date())                            //设置token的⽣成时间
                    .setId(user.getId() + "")               //设置⽤户id为token  id
                    .setClaims(userMap)                                     //map中可以存放⽤户的⻆⾊权限信息
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) //设置过期时间
                    .signWith(SignatureAlgorithm.HS256, "leiyuan666")     //设置加密⽅式和加密密码
                    .compact();
//            session.setAttribute("user",user.getId());
            return new R("1","登录成功",user,token);
        }
        return new R("401","验证码错误",null);
    }

}
