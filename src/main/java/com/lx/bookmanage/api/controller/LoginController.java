package com.lx.bookmanage.api.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.lx.bookmanage.common.utils.Result;
import com.lx.bookmanage.common.utils.ShiroUtils;
import com.lx.bookmanage.common.validator.Assert;

@Controller
@RequestMapping("/api")
public class LoginController {
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout() {
		SecurityUtils.getSubject().logout();

		return Result.ok("退出成功");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(String username, String password, String captcha,String type) {
		// 验证码检测
		try {
			Assert.isBlank(captcha, "验证码不能为空!");

			String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
			if (!captcha.equalsIgnoreCase(kaptcha)) {
				return Result.error("验证码不正确");
			}

			Assert.isBlank(username, "用户名不能为空!");
			Assert.isBlank(password, "密码不能为空!");
			Assert.isBlank(type,"请选定您的身份！");
			
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setHost(type);
			SecurityUtils.getSubject().login(token);

			ShiroUtils.setSessionAttribute("user", ShiroUtils.getUserEntity());

			return Result.ok("登录成功");

		} catch (Exception e) {
			return Result.error("用户名或者密码错误!");
		}
	}
}
