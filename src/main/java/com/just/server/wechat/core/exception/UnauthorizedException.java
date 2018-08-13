package com.just.server.wechat.core.exception;

/**
 * 未登录异常，返回HTTP状态码401
 *
 * @author wanghao
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UnauthorizedException() {
        super();
    }
}
