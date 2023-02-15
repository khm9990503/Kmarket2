package kr.co.kmarket2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsPolicyController {

	@GetMapping("termsPolicy/buyer")
	public String buyer() {
		return "termsPolicy/buyer";
	}
	
	@GetMapping("termsPolicy/finance")
	public String finance() {
		return "termsPolicy/finance";
	}
	
	@GetMapping("termsPolicy/location")
	public String location() {
		return "termsPolicy/location";
	}
	
	@GetMapping("termsPolicy/privacy")
	public String privacy() {
		return "termsPolicy/privacy";
	}
	
	@GetMapping("termsPolicy/seller")
	public String seller() {
		return "termsPolicy/seller";
	}
	
}
