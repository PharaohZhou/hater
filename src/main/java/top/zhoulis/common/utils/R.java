package top.zhoulis.common.utils;

import lombok.*;
import top.zhoulis.common.constants.CommonConstant;
import top.zhoulis.common.constants.enums.CommonEnum;

import java.io.Serializable;

/**
 *
 *
 */
@Builder
@ToString
@AllArgsConstructor
public class R<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstant.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(CommonEnum enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public R(Throwable e) {
        super();
        this.code = CommonConstant.ERROR;
        this.msg = e.getMessage();
    }

    public R(String message, Throwable e) {
        super();
        this.msg = message;
        this.code = CommonConstant.ERROR;
    }
}
