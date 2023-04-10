package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.OrderVO;
import kr.co.kmarket2.vo.PointVO;
import kr.co.kmarket2.vo.ReviewVO;

@Mapper
@Repository
public interface MyDAO {
	// 마이페이지 홈 주문 목록
	public List<OrderVO> selectOrdersIndex(String uid);
	// 마이페이지 홈 포인트 적립내역 목록
	public List<PointVO> selectMemberPointsIndex(String uid);
	// 마이페이지 홈 상품리뷰 목록
	public List<ReviewVO> selectReviewsIndex(String uid);
	// 마이페이지 홈 상품리뷰 등록
	public int insertReview(ReviewVO vo);
	
	// 나의 문의 리스트
	public List<ArticleVO> selectQnaArticles(String uid, int start);
	
	// 나의 문의 총 갯수
	public int selectQnaCountTotal(String uid, String group);
	
	// 나의 리뷰 불러오기
	public List<ReviewVO> selectReview(String uid, int start);
	
	// 나의 리뷰 총 갯수
	public int selectReviewCountTotal(String uid);
}
