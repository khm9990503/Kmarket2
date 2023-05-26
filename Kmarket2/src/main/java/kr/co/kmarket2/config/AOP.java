package kr.co.kmarket2.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import kr.co.kmarket2.security.MyUserDetails;
import kr.co.kmarket2.service.MyService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component
public class AOP {
	
	@Autowired
	private MyService service;
	
	// 마이페이지 헤더의 주문배송, 할인쿠폰, 포인트, 문의내역 갯수 출력을 위해 @GetMapping된 곳만 다음과 같은 작업이 실행된다.
	@Before("execution(* kr.co.kmarket2.controller.MyController.*(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping) && args(.., model, authentication)")
	public void beforeMyPage(JoinPoint joinPoint,Authentication authentication, Model model) {
		if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
	        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
	        String uid = userDetails.getUsername();
	        //log.info(uid);
	        // 사용자 정보 활용
	        int orderCount = service.selectMypageHeaderInfo(uid, 1);
	        int couponCount = service.selectMypageHeaderInfo(uid, 2);
	        int pointCount = service.selectMypageHeaderInfo(uid, 3);
	        int qnaCount = service.selectMypageHeaderInfo(uid, 4);
	        
	        model.addAttribute("orderCount",orderCount);
	        model.addAttribute("couponCount",couponCount);
	        model.addAttribute("pointCount",pointCount);
	        model.addAttribute("qnaCount",qnaCount);
	    }
		//log.info("before...");
	}
}
