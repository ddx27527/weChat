package com.just.server.wechat.core.exception;

/**
 * Token过期异常，返回HTTP状态码499
 *
 * @author wanghao
 */
public class TokenExpiredException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public TokenExpiredException() {
        super();
    }
}
