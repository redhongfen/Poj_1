package com.example.poj_1.controller;

import ch.qos.logback.core.util.StringUtil;
import cn.hutool.core.util.StrUtil;
import com.example.poj_1.common.AuthAccess;
import com.example.poj_1.common.Result;
import com.example.poj_1.eneity.Obj;
import com.example.poj_1.eneity.User;
import com.example.poj_1.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebController {
    @Resource
    UserService userService;
    @AuthAccess
    @GetMapping("/")
    public Result hello(String name){ return Result.success("success"); }

    @AuthAccess
    @PostMapping("/login")
    public Result login (@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("数据输入非法");
        }
        user=userService.login(user);
        return Result.success(user);
    }
    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");
        }
        if (user.getUsername().length() > 10 || user.getPassword().length() > 20) {
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return Result.success(user);
    }
}
