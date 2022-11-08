package com.lxb.springboot_vue_.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XBlib
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(Constans.CODE_200,"",null);
    }
    public static Result success(Object data) {
        return new Result(Constans.CODE_200,"",data);
    }
    public static Result error(String code,String msg) {
        return new Result(code,msg,null);
    }
    public static Result error(String msg) {
        return new Result(Constans.CODE_500,msg,null);
    }
}
