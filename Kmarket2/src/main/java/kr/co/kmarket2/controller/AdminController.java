package kr.co.kmarket2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("admin/index")
	public String index() {
		return "admin/index";
	}
	@GetMapping("admin/list")
	public String list() {
		return "admin/list";
	}
	@GetMapping("admin/register")
	public String register() {
		return "admin/register";
	}
}
