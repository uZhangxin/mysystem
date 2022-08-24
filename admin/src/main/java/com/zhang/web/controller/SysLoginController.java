package com.zhang.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.zhang.web.common.AjaxResult;
import com.zhang.web.common.RedisCache;
import com.zhang.web.common.constant.CacheConstants;
import com.zhang.web.common.constant.ConstantInfo;
import com.zhang.web.common.constant.HttpStatus;
import com.zhang.web.entity.SysUser;
import com.zhang.web.model.LoginBody;

import com.zhang.web.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangxin
 * @Date: 2022/8/10 9:58
 * @Description:
 */
@RestController
public class SysLoginController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;


    /**
     * 验证登录
     *
     * @param loginBody
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(LoginBody loginBody) {
        AjaxResult ajaxResult = AjaxResult.success();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", loginBody.getUserName())
                .eq("password", loginBody.getPassword());
        SysUser user = userService.getOne(queryWrapper);
        if (user == null) {
            ajaxResult.put("code", HttpStatus.ACCEPTED);
            ajaxResult.put("msg", "用户名密码不正确");
            return ajaxResult;
        }
        return AjaxResult.success();
    }

    /**
     * 生成验证码
     *
     * @return
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCodeImg() {
        AjaxResult ajaxResult = AjaxResult.success();
        // 生成redis key
        String uuid = UUID.randomUUID().toString();
        String codeKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        // 表达式图片流
        BufferedImage image = null;
        String capText = captchaProducerMath.createText();
        // 获取验证码文本
        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        // 获取验证码表达式
        String code = capText.substring(capText.lastIndexOf("@") + 1);
        // 获取表达式结果即验证码
        image = captchaProducerMath.createImage(capStr);
        // 将验证码值存入Redis并设置有效时间为
        redisCache.setCacheObject(codeKey, code, ConstantInfo.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
        ajaxResult.put("uuid", uuid);
        ajaxResult.put("img", os.toByteArray());
        return ajaxResult;
    }
}
