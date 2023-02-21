package kr.co.kmarket2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.kmarket2.service.MemberService;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.TermsVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("member/join")
	public String join() {
		
		return "member/join";
	}
	
	@GetMapping("member/login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("member/register")
	public String register() {
		
		return "member/register";
	}
	
	@GetMapping("member/registerSeller")
	public String registerSeller() {
		return "member/registerSeller";
	}
	
	@PostMapping("member/regSeller")
	public String regSeller(MemberVO vo, HttpServletRequest req) {
		String regIp = req.getRemoteAddr();
		vo.setRegip(regIp);
		service.insertSeller(vo);
		return "redirect:/member/login";
	}
	
	@PostMapping("member/regBuyer")
	public String regBuyer(MemberVO vo, HttpServletRequest req) {
		String regIp = req.getRemoteAddr();
		vo.setRegip(regIp);
		service.insertBuyer(vo);
		return "redirect:/member/login";
	}
	
	@GetMapping("member/signup")
	public String signup(Model model, String type) {
		TermsVO vo = service.selectTerms();
		model.addAttribute("vo", vo);
		model.addAttribute("type", type);
		return "member/signup";
	}


}
