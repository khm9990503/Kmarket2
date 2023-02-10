package kr.co.kmarket2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CsController {

	@GetMapping("cs/index")
	public String index() {
		return "cs/index";
	}
	
	@GetMapping("cs/qna/list")
	public String qnaList() {
		return "cs/qna/list";
	}
	
	
	
}
