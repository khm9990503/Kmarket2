package kr.co.kmarket2.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket2.entity.MemberEntity;
import kr.co.kmarket2.security.MyUserDetails;
import kr.co.kmarket2.service.MyService;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.CouponVO;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.OrderVO;
import kr.co.kmarket2.vo.PointVO;
import kr.co.kmarket2.vo.ReviewVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class MyController {

	@Autowired
	private MyService service;
	
	@GetMapping("my/home")
	public String home(@AuthenticationPrincipal MyUserDetails member, String group,Model model) {
		String uid = member.getUser().getUid();
		List<OrderVO> orders = service.selectOrdersIndex(uid);
		for(OrderVO order : orders) {
			char isJ  = order.getThumb1().charAt(1);
			if(isJ == 'J') {
				order.setThumb1(order.getThumb1().replaceFirst("/Java1_Kmarket1", ""));
			}
		}
		List<PointVO> points = service.selectMemberPointsIndex(uid);
		List<ReviewVO> reviews = service.selectReviewsIndex(uid);
		List<ArticleVO> articles = service.selectQnaArticles(uid, 0);
		model.addAttribute("articles",articles);
		model.addAttribute("reviews",reviews);
		model.addAttribute("orders",orders);
		model.addAttribute("points",points);
		model.addAttribute("group",group);
		model.addAttribute("uid",uid);
		model.addAttribute("member",member.getUser());
		
		return "my/home";
	}
	
	@ResponseBody
	@GetMapping("my/home/receive")
	public int homeReceive(int ordNo) {
		return service.updateOrderComplete(ordNo);
	}
	@ResponseBody
	@GetMapping("my/home/seller")
	public MemberVO homeSeller(String company) {
		return service.selectSellerIndex(company);
	}
	
	@PostMapping("my/home/review")
	public String homeReview(ReviewVO vo, HttpServletRequest req) {
		vo.setRegip(req.getRemoteAddr());
		service.insertReview(vo);
		return "redirect:/my/review";
	}
	
	@GetMapping("my/ordered")
	public String ordered( String group,Model model) {
		
		
		
		model.addAttribute("group",group);
		return "my/ordered";
	}
	@GetMapping("my/point")
	public String point(String group,@AuthenticationPrincipal MyUserDetails member,String pg,Model model) {
		String uid = member.getUsername();
		// 페이징
		int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		
		total = service.selectPointsCountTotal(uid,8); //전체 리뷰 갯수
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
		
		// points 목록 가져오기
		List<PointVO> points= service.selectPoints(uid, 8, start);
		
		model.addAttribute("points",points);
		model.addAttribute("group",group);
		model.addAttribute("uid",uid);
		model.addAttribute("pg",pg);
		return "my/point";
	}
	
	@ResponseBody
	@GetMapping("my/pointBySort")
	public Map<String, Object> pointBySort(String uid, String pg, @RequestParam(name = "sort", required = false) Integer sort, String srt, String end){
		// 페이징
		int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		if(srt != null) {
			total = service.selectPointsCountDuring(uid, srt, end);
			log.info("srt가 null이 아닙니다.");
		}else {
			total = service.selectPointsCountTotal(uid,sort); //전체 포인트내역 갯수
			log.info("srt가 null입니다.");
		}
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		// points 목록 가져오기
		List<PointVO> points = new ArrayList<>();
		if(srt != null) {
			points = service.selectPointsDuring(uid, start, srt, end);
			log.info("srt가 null이 아닙니다.");
		}else {
			points = service.selectPoints(uid, sort, start);
			log.info("srt가 null입니다.");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("lastPageNum", lastPageNum);
		map.put("currentPage", currentPage);
		map.put("pageGroupStart", result[0]);
		map.put("pageGroupEnd", result[1]);
		map.put("pageStartNum", pageStartNum+1);
		map.put("points", points);
		
		return map;
	}
	
	@GetMapping("my/coupon")
	public String coupon(String group,@AuthenticationPrincipal MyUserDetails member, Model model) {
		List<CouponVO> coupons = service.selectCoupons(member.getUsername());
		model.addAttribute("coupons",coupons);
		model.addAttribute("group",group);
		return "my/coupon";
	}
	@GetMapping("my/pwChange")
	public String pwChange(@AuthenticationPrincipal MyUserDetails member, String group,Model model) {
		MemberEntity user = member.getUser();
		model.addAttribute("user", user);
		model.addAttribute("group",group);
		return "my/pwChange";
	}
	@GetMapping("my/info")
	public String info(@AuthenticationPrincipal MyUserDetails member, String group,Model model) {
		MemberEntity user = member.getUser();
		model.addAttribute("user", user);
		model.addAttribute("group",group);
		return "my/info";
	}
	@ResponseBody
	@PutMapping("my/info")
	public int updateMemberInfo(@AuthenticationPrincipal MyUserDetails member,MemberVO vo) {
		MemberEntity user = member.getUser();
		user.setEmail(vo.getEmail());
		user.setHp(vo.getHp());
		user.setZip(vo.getZip());
		user.setAddr1(vo.getAddr1());
		user.setAddr2(vo.getAddr2());
		return service.updateMemberInfo(vo);
	}
	@PostMapping("my/info/pwChange")
	public String pwChange(String uid, String pass2) {
		service.updatePassword(uid, pass2);
		return "redirect:/member/logout";
	}
	@ResponseBody
	@DeleteMapping("my/info/dropMember")
	public int dropMember(String uid) {
		return service.dropMember(uid);
	}
	
	@GetMapping("my/review")
	public String review(String group, String pg, Model model,Principal principal) {
		
		//아이디 가져오기
		String uid = principal.getName();
		
		// 페이징
		int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		
		total = service.selectReviewCountTotal(uid); //전체 리뷰 갯수
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
		
		// review 목록(prodName join) 가져오기
		List<ReviewVO> reviews = service.selectReview(uid, start);
		
		model.addAttribute("reviews", reviews);
		
		model.addAttribute("group", group);
		return "my/review";
	}
	@GetMapping("my/qna")
	public String qna(String group, String pg ,Model model, Principal principal) {
		
		//아이디 가져오기
		String uid = principal.getName();
		
		//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		
		total = service.selectQnaCountTotal(uid, group); //전체 게시물 갯수
		
			
		
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
