package kr.co.kmarket2.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.TermsVO;

@Mapper
@Repository
public interface MemberDAO {
	

	public TermsVO selectTerms();
	public void insertBuyer(MemberVO vo);
	public void insertSeller(MemberVO vo);
	public int selectCountUid(String uid);

}
