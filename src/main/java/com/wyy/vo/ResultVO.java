package com.wyy.vo;

import lombok.Data;

@Data
public class ResultVO<T>
{
    private Integer code;

    private T data;

    private String message;

    private static final int SUCCESS_CODE = 200;

    private static final int FAIL_CODE = 500;

    public ResultVO() {}

    public ResultVO(Integer code, T data, String message)
    {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public static <T> ResultVO<T> success(T data)
    {
        return new ResultVO<>(SUCCESS_CODE, data, "");
    }

    public static <T> ResultVO<T> success()
    {
        return new ResultVO<>(SUCCESS_CODE, null, "success");
    }

    public static <T> ResultVO<T> fail(String message)
    {
        return new ResultVO<>(FAIL_CODE, null, message);
    }
    public static <T> ResultVO<T> fail(Integer code, String message)
    {
        return new ResultVO<>(code, null, message);
    }




}
