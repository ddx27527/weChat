package com.just.server.wechat.core.exception;

/**
 * 无权访问异常，返回HTTP状态码403
 *
 * @author wanghao
 */
public class ForbiddenException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ForbiddenException() {
        super();
    }
}
