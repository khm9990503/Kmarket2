package kr.co.kmarket2.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket2.service.AdminService;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@GetMapping("admin/index")
	public String index() {
		return "admin/index";
	}
	@GetMapping("admin/product/list")
	public String list(Model model, String pg, Principal principal) {
		
		int currentPage = service.getCurrnetPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.selectCountTotal();
		int pageStartNum = service.getPageStartNum(total, start);
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = service.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = service.getPageGroup(currentPage, lastPageNum)[1];
		
		List<ProductVO> products = service.selectProducts(start);
		
		model.addAttribute("pg", pg);
		model.addAttribute("pageStartNum", pageStartNum-1);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageGroupStart", pageGroupStart);
		model.addAttribute("pageGroupEnd", pageGroupEnd);
		model.addAttribute("products", products);
		
		return "admin/product/list";
	}
	@GetMapping("admin/product/register")
	public String register(Model model) {
		List<Cate1VO> cate1s = service.selectCate1();
		model.addAttribute("cate1s",cate1s);
		return "admin/product/register";
	}
	@GetMapping("admin/product/modify")
	public String modify(Model model) {
		
		return "admin/product/modify";
	}
	@ResponseBody
	@GetMapping("admin/product/cate2List")
	public Map<String, List<Cate2VO>> cate2List(Model model, int cate1) {
		List<Cate2VO> cate2s = service.selectCate2(cate1);
		Map<String, List<Cate2VO>> result = new HashMap<>();
		result.put("cate2s", cate2s);
		return result;
	}
	@PostMapping("admin/product/register")
	public String register(ProductVO vo, HttpServletRequest req) {
		vo.setIp(req.getRemoteAddr()); 
		int result = service.insertProduct(vo);
		return "redirect:/admin/product/list";
	}
	@PostMapping("admin/product/delete")
	public String delete(String[] prodNo, HttpServletRequest req) {
		int result = service.deleteProduct(prodNo);
		return "redirect:/admin/product/list";
	}
}
