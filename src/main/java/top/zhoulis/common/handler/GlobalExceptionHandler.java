package top.zhoulis.common.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zhoulis.common.constants.CommonConstant;
import top.zhoulis.common.exception.GlobalException;
import top.zhoulis.common.utils.R;

/**
 * 全局异常处理器
 *
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e) {
        e.printStackTrace();
        return new R(e);
    }

    @ExceptionHandler(value = GlobalException.class)
    public R globalExceptionHandle(GlobalException e) {
        e.printStackTrace();
        return new R<>(CommonConstant.ERROR, e.getMsg());
    }
}
