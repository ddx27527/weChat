package com.just.server.wechat.core.advice;

import com.just.server.wechat.core.helper.JsonHelper;
import com.just.server.wechat.core.model.RestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:01
 * Description: 统一封装Controller的返回值
 */
@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalResponseBodyAdvice.class);
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        logger.info("request....");
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof RestModel) {
            return body;
        }
        RestModel restModel = new RestModel();
        restModel.setData(body);
        if (body == null) {
            return null;
        }
        if (body instanceof String) {
            return JsonHelper.getGson().toJson(restModel);
        }
        logger.info("返回结果：",restModel);
        return restModel;
    }
}
