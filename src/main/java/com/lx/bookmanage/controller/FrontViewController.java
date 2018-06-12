package com.lx.bookmanage.controller;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration
@Controller
public class FrontViewController {

	
	
	@RequestMapping("/")
	public String index(ModelMap map){
		
		
		
		return "login";
	}
	
	@RequestMapping("/notice/{type}")
	public String list(ModelMap map, 
			@PathVariable Integer type, 
			@RequestParam(value = "page", defaultValue = "1") Integer page){
		
		
		
		
		if(type == 1){
			map.addAttribute("name", "通知");
		} else if(type == 2){
			map.addAttribute("name", "公告");
		} else if(type == 3){
			map.addAttribute("name", "下载");
		} else {
			map.addAttribute("name", "");
		}
		
		return "front/list";
	}
	
	
}
