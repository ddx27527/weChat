package com.just.server.wechat.core.service;

import com.just.server.wechat.core.configure.KongConfig;
import com.just.server.wechat.core.helper.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:21
 * Description: No Description
 */
public class BaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected SqlHelper sqlHelper;

    @Deprecated
    @Value("${kong.adminUrl}")
    protected String kongHost;

    @Autowired
    protected KongConfig kongConfig;
}
