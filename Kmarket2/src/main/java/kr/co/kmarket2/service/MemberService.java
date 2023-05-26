package kr.co.kmarket2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmarket2.dao.MemberDAO;
import kr.co.kmarket2.vo.CouponVO;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.TermsVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private PasswordEncoder encoder;

	public TermsVO selectTerms() {
		return dao.selectTerms();
	};
	
	@Transactional
	public void insertBuyer(MemberVO vo){
		String Pass2 = vo.getPass2();
		vo.setPass(encoder.encode(Pass2));
		dao.insertBuyer(vo);
		CouponVO coupon = new CouponVO();
		coupon.setUid(vo.getUid());
		coupon.setName("회원가입 축하 5,000원 할인쿠폰");
		coupon.setDiscount(5000);
		coupon.setLimit(5100);
		coupon.setState(0);
		dao.insertCoupon(coupon);
	};
	
	public void insertSeller(MemberVO vo){
		String Pass2 = vo.getPass2();
		vo.setPass(encoder.encode(Pass2));
		dao.insertSeller(vo);
	};
	
	
	public int selectCountUid(String uid) {
		return dao.selectCountUid(uid);
	};
}
