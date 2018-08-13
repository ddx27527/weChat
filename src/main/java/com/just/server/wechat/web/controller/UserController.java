package com.just.server.wechat.web.controller;

import com.just.server.wechat.core.controller.BaseController;
import com.just.server.wechat.web.model.BackUserModel;
import com.just.server.wechat.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 13:57
 * Description: No Description
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/chat/back/user")
    public Object getBackUser(BackUserModel model){
        return userService.getBackUser(model);
    }

}
