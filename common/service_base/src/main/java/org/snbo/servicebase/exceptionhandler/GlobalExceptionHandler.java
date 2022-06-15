package org.snbo.servicebase.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.snbo.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunbo
 * @create 2022-03-18-14:24
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("服务器报错了,快去康康");
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(MoguException.class)
    @ResponseBody
    public R error(MoguException me){
        log.error(me.getMessage());
        me.printStackTrace();
        return R.error().code(me.getCode()).message(me.getMsg());
    }
}
