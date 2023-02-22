package kr.co.kmarket2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket2.service.CompanyService;
import kr.co.kmarket2.vo.ArticleVO;

@Controller
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@GetMapping("company/index")
	public String index() {
		return "company/index";
	}
	
	@GetMapping("company/introduce")
	public String introduce() {
		return "company/introduce";
	}
	
	@GetMapping("company/manage")
	public String manage() {
		return "company/manage";
	}
	
	@GetMapping("company/notice")
	public String notice(Model model, String group, String pg) {
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		
		total = service.selectCountTotal(group); //전체 게시물 갯수
		
			
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
    			
		//전체 목록 가져오기
		List<ArticleVO> articles = service.selectComArticles(group, start);
		
    	
    	
    	model.addAttribute("articles", articles);
    
		
		
		return "company/notice";
	}
	
	@GetMapping("company/promote")
	public String promote() {
		return "company/promote";
	}

}
