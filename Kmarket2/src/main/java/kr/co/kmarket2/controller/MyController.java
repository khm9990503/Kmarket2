package kr.co.kmarket2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

	@GetMapping("my/home")
	public String home() {
		return "my/home";
	}
	@GetMapping("my/ordered")
	public String ordered() {
		return "my/ordered";
	}
	@GetMapping("my/point")
	public String point() {
		return "my/point";
	}
	@GetMapping("my/coupon")
	public String coupon() {
		return "my/coupon";
	}
	@GetMapping("my/info")
	public String info() {
		return "my/info";
	}
	@GetMapping("my/qna")
	public String qna() {
		return "my/qna";
	}
}
