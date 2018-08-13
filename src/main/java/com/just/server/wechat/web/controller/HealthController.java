package com.just.server.wechat.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 15:30
 * Description: 用于KONG统一网关健康检查
 */
@RestController
public class HealthController {

    @GetMapping("/")
    public Object healthCheck() {
        return "I'm still alive!";
    }
}
