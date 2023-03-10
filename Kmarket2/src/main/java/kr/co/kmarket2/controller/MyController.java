package kr.co.kmarket2.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket2.service.MyService;
import kr.co.kmarket2.vo.ArticleVO;

@Controller
public class MyController {

	@Autowired
	private MyService service;
	
	@GetMapping("my/home")
	public String home(String group,Model model) {
		
		model.addAttribute("group",group);
		
		return "my/home";
	}
	@GetMapping("my/ordered")
	public String ordered(String group,Model model) {
		
		model.addAttribute("group",group);
		return "my/ordered";
	}
	@GetMapping("my/point")
	public String point(String group,Model model) {
		
		model.addAttribute("group",group);
		return "my/point";
	}
	@GetMapping("my/coupon")
	public String coupon(String group,Model model) {
		
		model.addAttribute("group",group);
		return "my/coupon";
	}
	@GetMapping("my/info")
	public String info(String group,Model model) {
		
		model.addAttribute("group",group);
		return "my/info";
	}
	@GetMapping("my/review")
	public String review() {
		return "my/review";
	}
	@GetMapping("my/qna")
	public String qna(String group, String pg ,Model model, Principal principal) {
		
		//아이디 가져오기
		String uid = principal.getName();
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		
		total = service.selectCountTotal(uid, group); //전체 게시물 갯수
		
			
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("group", group);
		
		// qna 목록(c1Name join) 가져오기
		List<ArticleVO> articles = service.selectQnaArticles(uid, start);
		
		model.addAttribute("articles", articles);
		
		return "my/qna";
	}
}
