package com.just.server.wechat.core.controller;

import com.just.server.wechat.core.model.AuthModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:14
 * Description: No Description
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected AuthModel authModel = new AuthModel();

    public AuthModel getAuthModel() {
        return authModel;
    }

    public void setAuthModel(AuthModel authModel) {
        this.authModel = authModel;
    }
}
