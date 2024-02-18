package com.leiyuan.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leiyuan.common.R;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        if(token == null){
            doResponse(response,new R("99999","请先登录",null));//提示请先登录
        }else{
            try {
                //验证token
                JwtParser parser = Jwts.parser();
                //解析token的SigningKey必须和⽣成token时设置密码⼀致
                parser.setSigningKey("leiyuan666");
                //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
                Jws<Claims> claimsJws =parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                doResponse(response,new R("99998","身份认证过期，请重新登录",null));
            }catch (UnsupportedJwtException e){
                doResponse(response,new R("99997","身份非法",null));
            }catch (Exception e){
                doResponse(response,new R("99996","网络异常",null));
            }
        }
        return false;
    }
    private void doResponse(HttpServletResponse response,R r) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(r);
        out.print(s);
        out.flush();
        out.close();
    }
}
