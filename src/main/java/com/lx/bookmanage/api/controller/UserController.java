package com.lx.bookmanage.api.controller;

import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lx.bookmanage.common.utils.Result;
import com.lx.bookmanage.common.utils.ShiroUtils;
import com.lx.bookmanage.common.validator.Assert;
import com.lx.bookmanage.pojo.User;
import com.lx.bookmanage.repository.AdminRopository;
import com.lx.bookmanage.repository.StudentRopository;

@Controller
@RequestMapping("/")
public class UserController {
	@Autowired
	private StudentRopository studentRopository;
	
	@Autowired
	private AdminRopository adminRopository;

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(String password, String newPassword, String confirmPassword) {
		// 验证码检测
		try {
			Assert.isBlank(password, "原密码不能为空!");
			Assert.isBlank(newPassword, "新密码不能为空!");
			Assert.isBlank(confirmPassword, "确认密码不能为空!");

			if (!newPassword.equals(confirmPassword)) {
				return Result.error("新密码和确认密码不一致!");
			}

			User user = ShiroUtils.getUserEntity();

			Md5Hash hash = new Md5Hash(password, "ewaytek", 2);
			if (!hash.toString().equals(user.getPassword())) {
				return Result.error("原密码输入错误!");
			}

			Md5Hash newhash = new Md5Hash(newPassword, "ewaytek", 2);

			if (user.getType().equals("0")) {
				studentRopository.updatePassword(user.getId(), newhash.toString());
			} else {
				adminRopository.updatePassword(user.getId(), newhash.toString());
			}

			

			return Result.ok("密码修改成功");

		} catch (Exception e) {
			return Result.error("密码修改失败!");
		}
	}

}
