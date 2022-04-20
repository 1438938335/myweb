package com.wyy.controller;

import com.wyy.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController
{
    @ExceptionHandler(Exception.class)
    public ResultVO<String> message(Exception e)
    {
        log.error("前台调用接口异常", e);
        return ResultVO.fail(e.getMessage());
    }
}
