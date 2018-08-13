package com.just.server.wechat.core.handler;

import com.just.server.wechat.core.exception.*;
import com.just.server.wechat.core.model.RestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:05
 * Description: 拦截系统的所有异常，统一封装返回消息体
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ResponseEntity defaultErrorHandler(Exception e) {
        RestModel restModel = new RestModel();
        restModel.setMessage(e.getMessage());
        ResponseEntity responseEntity;
        if (e instanceof BadRequestException) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restModel);
        } else if (e instanceof ForbiddenException) {
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(restModel);
        } else if (e instanceof UnauthorizedException) {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restModel);
        } else if (e instanceof TokenExpiredException) {
            restModel.setMessage("很抱歉，您的会话已过期，请重新登录");
            responseEntity = ResponseEntity.status(499).body(restModel);
        } else if (e instanceof SystemException) {
            restModel.setMessage("很抱歉，系统异常，请稍后重试，如有疑问，请联系系统管理员");
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restModel);
        } else {
            restModel.setMessage("很抱歉，您的请求是非法的，如有疑问，请联系系统管理员");
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restModel);
        }
        return responseEntity;
    }

}
