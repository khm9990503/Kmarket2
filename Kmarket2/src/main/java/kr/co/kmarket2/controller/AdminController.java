package kr.co.kmarket2.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket2.service.AdminService;
import kr.co.kmarket2.vo.ArticleCateVO;
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
	public String list(Model model, String pg, Principal principal, String search, String type) {
		
		int currentPage = service.getCurrnetPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = 0;
		if(search == null) {
			total = service.selectCountTotal();
		}else {
			total = service.selectCountTotalSearch(search, type);
		}
		int pageStartNum = service.getPageStartNum(total, start);
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = service.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = service.getPageGroup(currentPage, lastPageNum)[1];
		List<ProductVO> products = null;
		if(search == null) {
			products = service.selectProducts(start);
		}else {
			products = service.searchProducts(search, type, start);
		}
		
		for(ProductVO product : products) {
			char isJ  = product.getThumb1().charAt(1);
			if(isJ == 'J') {
				product.setThumb1(product.getThumb1().replaceFirst("/Java1_Kmarket1", ""));
				product.setThumb2(product.getThumb2().replaceFirst("/Java1_Kmarket1", ""));
				product.setThumb3(product.getThumb3().replaceFirst("/Java1_Kmarket1", ""));
				product.setDetail(product.getDetail().replaceFirst("/Java1_Kmarket1", ""));
			}
		}
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
	@ResponseBody
	@GetMapping("admin/product/delete")
	public Map<String, Integer> delete(String prodNo, HttpServletRequest req) {
		List<String> list = Arrays.asList(prodNo.split(","));
		int result = 0;
		for(String no : list) {
			result = service.deleteProduct(no);
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	/////// 관리자 고객센터 관련 /////////////////////////////////////////////
	@GetMapping("admin/cs/list")
	public String list(Model model,String group, String cate, String cate2 ,  String pg) {
		Map<String,String> cates = new LinkedHashMap<>();
		if(group.equals("notice")) {
			cates.put("service", "고객서비스");
			cates.put("deal", "안전거래");
			cates.put("danger", "위해상품");
			cates.put("lucky", "이벤트당첨");
		}else {
			cates.put("member", "회원");
			cates.put("event", "쿠폰/이벤트");
			cates.put("order", "주문/결제");
			cates.put("deli", "배송");
			cates.put("cancle", "취소/반품/교환");
			cates.put("trip", "여행/항공/숙박");
			cates.put("safe", "안전거래");
			if(cate == null) {
				cate = "member";
			}
		}
		// 페이지//
		int currentPage = service.getCurrnetPage(pg);
		int start = service.getLimitStart(currentPage);
		int	total = 0;
		List<ArticleCateVO> artiCate2s = new ArrayList<>();
		// 전체 게시물 갯수
		if(group.equals("notice")) {
			if(cate==null) {
				total = service.selectCountTotalArticle(group,cate,cate2,"1");
			}else {
				total = service.selectCountTotalArticle(group,cate,cate2,"2");
			}
		}else if(group.equals("faq") || group.equals("qna")) {
			artiCate2s = service.selectArticeCates();
			if(cate == null) {
				cate = "member";
			}
			if(cate2 == null) {
				cate2 = "reg";
			}
			total = service.selectCountTotalArticle(group,cate,cate2,"3");
		}
		int pageStartNum = service.getPageStartNum(total, start);
		int lastPageNum = service.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = service.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = service.getPageGroup(currentPage, lastPageNum)[1];
		
		// 게시물 가져오기
		List<ArticleVO> articles = null;
		
		if(group.equals("notice")) {
			if(cate==null) {
				articles = service.selectArticlesByGroup(group, cate, cate2, start, "1");
			}else {
				articles = service.selectArticlesByGroup(group, cate, cate2, start, "2");
			}
		}else if(group.equals("faq") || group.equals("qna")) {
			articles = service.selectArticlesByGroup(group, cate, cate2, start, "3");
		}
		model.addAttribute("pg", pg);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageGroupStart", pageGroupStart);
		model.addAttribute("pageGroupEnd", pageGroupEnd);
		model.addAttribute("cates",cates);
		model.addAttribute("group",group);
		model.addAttribute("cate",cate);
		model.addAttribute("cate2",cate2);
		model.addAttribute("articate2s",artiCate2s);
		model.addAttribute("articles",articles);
		return "admin/cs/list";
	}
	@ResponseBody
	@GetMapping("/admin/cs/cateList")
	public Map<String, List<ArticleCateVO>> cateList(String cate) {
		List<ArticleCateVO> cates = service.selectArticeCatesByCate(cate);
		Map<String, List<ArticleCateVO>> map = new HashMap<>();
		map.put("result", cates);
		return map;
	}
	
	@ResponseBody
	@GetMapping("/admin/cs/listDelete")
	public Map<String, Integer> listDelete(String chks){
		List<String> chkList = Arrays.asList(chks.split(","));
		int result = 0;
		for(String chk : chkList) {
			result = service.deleteArticles(chk);
		}
		Map<String , Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	@GetMapping("admin/cs/write")
	public String write(Model model, String group, String cate) {
		Map<String,String> cates = service.getCates(group, cate);
		model.addAttribute("cates",cates);
		model.addAttribute("group",group);
		model.addAttribute("cate",cate);
		return "admin/cs/write";
	}
	@RequestMapping(value="admin/cs/write", method = {RequestMethod.POST})
	public String write(ArticleVO vo, String group,HttpServletRequest req) {
		vo.setRegip(req.getRemoteAddr());
		int result = service.insertArticle(vo);
		return "redirect:/admin/cs/list?group="+group+"&result="+result;
	}
	@GetMapping("admin/cs/modify")
	public String modify(Model model, String group, String cate, String no) {
		Map<String,String> cates = service.getCates(group, cate);
		List<ArticleCateVO> artiCate2s = service.selectArticeCates();
		ArticleVO article = service.selectArticle(no);
		model.addAttribute("cates",cates);
		model.addAttribute("artiCate2s",artiCate2s);
		model.addAttribute("no",no);
		model.addAttribute("group",group);
		model.addAttribute("cate",cate);
		model.addAttribute("article",article);
		return "admin/cs/modify";
	}
	
	@RequestMapping(value="admin/cs/modify", method = {RequestMethod.POST})
	public String modify(ArticleVO vo) {
		int result = service.updateArticle(vo);
		return "redirect:/admin/cs/view?no="+vo.getNo()+"&result="+result+"&group="+vo.getGroup();
	}
	
	@GetMapping("admin/cs/view")
	public String view(Model model, String group, String cate, String no) {
		Map<String,String> cates = service.getCates(group, cate);
		List<ArticleCateVO> artiCate2s = service.selectArticeCates();
		ArticleVO article = service.selectArticle(no);
		ArticleVO reply = service.selectReply(no);
		model.addAttribute("cates",cates);
		model.addAttribute("artiCate2s",artiCate2s);
		model.addAttribute("group",group);
		model.addAttribute("cate",cate);
		model.addAttribute("article",article);
		model.addAttribute("reply",reply);
		return "admin/cs/view";
	}
	@ResponseBody
	@RequestMapping(value="admin/cs/view", method = {RequestMethod.POST})
	public Map<String, Integer> reply(String no, String uid, String content, HttpServletRequest req) {
		int result = service.insertReply(no, uid, content, req.getRemoteAddr());
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	@GetMapping("admin/cs/delete")
	public String delete(String no,String group) {
		service.deleteArticles(no);
		return "redirect:/admin/cs/list?group="+group;
	}
}
