package com.tyy.y2021.m04.d29.dto;


import lombok.Data;

/**
 * @author:tyy
 * @date:2021/4/29
 */

@Data
public class Result<success> {

    private String msg;

    private Integer code;

    private Object data;

    public static Result fail(String msg){
        return null;
    }

    public static Result success(Object obj) {
        return getResult(ResultEnum.SUCCESS, obj);
    }

    public static Result success() {
        return getResult(ResultEnum.SUCCESS, null);
    }

    public static Result getResult(ResultEnum re) {
        return getResult(re, null);
    }

    public static Result getResult(ResultEnum re, Object obj) {
        return getResult(re.getCode(), re.getMessage(), obj);
    }

    public static Result getResult(Integer code, String msg) {
        return getResult(code, msg, null);
    }


    public static Result getResult(Integer code, String msg, Object obj) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);
        return result;
    }
}
