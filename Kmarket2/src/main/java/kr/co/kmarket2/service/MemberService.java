package kr.co.kmarket2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.MemberDAO;
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
	
	public void insertBuyer(MemberVO vo){
		String Pass2 = vo.getPass2();
		vo.setPass(encoder.encode(Pass2));
		dao.insertBuyer(vo);
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
