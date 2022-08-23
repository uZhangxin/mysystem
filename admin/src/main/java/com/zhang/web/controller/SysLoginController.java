package com.zhang.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.web.common.AjaxResult;
import com.zhang.web.common.HttpStatus;
import com.zhang.web.entity.SysUser;
import com.zhang.web.model.LoginBody;

import com.zhang.web.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangxin
 * @Date: 2022/8/10 9:58
 * @Description:
 */
@RestController
public class SysLoginController {

    @Autowired
    ISysUserService userService;

    @PostMapping("/login")
    public AjaxResult login(LoginBody loginBody) {
        AjaxResult ajaxResult = AjaxResult.success();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", loginBody.getUserName())
                .eq("password", loginBody.getPassword());
        SysUser user = userService.getOne(queryWrapper);
        if (user == null) {
            ajaxResult.put("code", HttpStatus.ACCEPTED);
            ajaxResult.put("msg","用户名密码不正确");
            return ajaxResult;
        }
        return AjaxResult.success();
    }

    @GetMapping("/captchaImage")
    public AjaxResult getCodeImg(){
        return null;
    }
}
