package kr.co.kmarket2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.MemberDAO;
import kr.co.kmarket2.vo.TermsVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO dao;

	public TermsVO selectTerms() {
		return dao.selectTerms();
	};
	
}
