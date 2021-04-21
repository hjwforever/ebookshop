package com.aruoxi.ebookshop.filter;

import com.aruoxi.ebookshop.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.aruoxi.ebookshop.common.CommonResult.fail;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult<Object> handle(Exception exception) {
        LOGGER.error("统一异常处理：", exception);
        return CommonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

}
