package com.lx.bookmanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManageViewController {
	@RequestMapping("/")
	public String index(){
		return "redirect:/manage/dashboard";
	}
	@RequestMapping("/dashboard")
	public String dashboard(){
		return "manage/dashboard";
	}
	@RequestMapping("/findbook")
	public String find(){
		return "manage/findbook";
	}
	@RequestMapping("/student")
	public String studentmessage(){
		return "manage/student";
	}
	@RequestMapping("/managestudent")
	public String managesudent(){
		return "manage/managestudent";
	}
	@RequestMapping("/managebook")
	public String managebook(){
		return "manage/managebook";
	}
}
