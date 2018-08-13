package com.just.server.wechat.core.aop;

import com.just.server.wechat.core.helper.SecurityHelper;
import com.just.server.wechat.core.controller.BaseController;
import com.just.server.wechat.core.exception.ForbiddenException;
import com.just.server.wechat.core.exception.TokenExpiredException;
import com.just.server.wechat.core.exception.UnauthorizedException;
import com.just.server.wechat.core.model.AuthModel;
import com.just.server.wechat.core.model.RestModel;
import io.undertow.util.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:10
 * Description: 拦截所有业务级别controller
 * 用来封装统一格式，捕获业务异常，其他服务端异常使用ExceptionHandler来捕捉
 */
public class ControllerAop {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Around("within(com.just..*.controller..*)")
    public Object process(ProceedingJoinPoint point) {
        Object result;
        ResponseEntity responseEntity;
        RestModel restModel = new RestModel();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            logger.info(request.getHeaderNames().toString());
            String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotEmpty(authorization)) {
                logger.info(authorization);
                AuthModel authModel = SecurityHelper.getAuthModel(authorization);
                if (authModel != null) {
                    BaseController baseController = (BaseController) point.getTarget();
                    baseController.setAuthModel(authModel);
                }
            }
            result = point.proceed();
            restModel.setData(result);
            responseEntity = ResponseEntity.ok(restModel);
        } catch (BadRequestException badRequestException) {
            restModel.setMessage(badRequestException.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restModel);
        } catch (ForbiddenException forbiddenException) {
            restModel.setMessage(forbiddenException.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(restModel);
        } catch (UnauthorizedException unauthorizedException) {
            restModel.setMessage(unauthorizedException.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restModel);
        } catch (TokenExpiredException tokenExpiredException) {
            restModel.setMessage("很抱歉，您的会话已过期，请重新登录");
            responseEntity = ResponseEntity.status(499).body(restModel);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            restModel.setMessage("很抱歉，系统异常，请联系系统管理员");
            responseEntity = ResponseEntity.status(500).body(restModel);
        }
        return responseEntity;
    }
}
