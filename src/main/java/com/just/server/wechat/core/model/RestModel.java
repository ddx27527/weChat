package com.just.server.wechat.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.just.server.wechat.core.helper.JsonHelper;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:03
 * Description: 统一异常返回json消息体
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestModel<E> extends BaseModel {

    private String message;
    private E data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JsonHelper.getGson().toJson(this);
    }

}
