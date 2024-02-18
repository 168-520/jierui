package com.leiyuan.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    private String code;
    private String message;
    private Object data;
    private String token;

    public R(String code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
