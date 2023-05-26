package kr.co.kmarket2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.MyDAO;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.CouponVO;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.OrderVO;
import kr.co.kmarket2.vo.PointVO;
import kr.co.kmarket2.vo.ProductVO;
import kr.co.kmarket2.vo.ReviewVO;

@Service
public class MyService {

	@Autowired
	private MyDAO dao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	// 마이페이지 메인 주문 목록
	public List<OrderVO> selectOrdersIndex(String uid){
		List<OrderVO> orders = dao.selectOrdersIndex(uid);
		for(OrderVO order : orders) {
			char isJ  = order.getThumb1().charAt(1);
			if(isJ == 'J') {
				order.setThumb1(order.getThumb1().replaceFirst("/Java1_Kmarket1", ""));
			}
		}
		return orders;
	};
	
	// 마이페이지 홈 포인트 적립내역 목록
	public List<PointVO> selectMemberPointsIndex(String uid){
		return dao.selectMemberPointsIndex(uid);
	};
	// 마이페이지 홈 상품리뷰 목록
	public List<ReviewVO> selectReviewsIndex(String uid){
		return dao.selectReviewsIndex(uid);
	};
	// 마이페이지 홈 상품리뷰 등록
	public int insertReview(ReviewVO vo) {
		return dao.insertReview(vo);
	};
	// 마이페이지 홈 수취확인 업데이트
	public int updateOrderComplete(int ordNo) {
		return dao.updateOrderComplete(ordNo);
	};
	// 마이페이지 홈 판매자정보 보기
	public MemberVO selectSellerIndex(String company) {
		return dao.selectSellerIndex(company);
	};
	// 마이페이지 홈 문의하기
	public int insertQna(ArticleVO vo) {
		return dao.insertQna(vo);
	};
	// 마이페이지 헤더 정보 불러오기
	public int selectMypageHeaderInfo(String uid, int sort) {
		return dao.selectMypageHeaderInfo(uid, sort);
	};
	// 나의 전체주문내역 목록
	public List<OrderVO> selectOrders(String uid, int sort, int start, String srt, String end){
		List<OrderVO> orders = dao.selectOrders(uid, sort, start, srt, end);
		for(OrderVO order : orders) {
			char isJ  = order.getThumb1().charAt(1);
			if(isJ == 'J') {
				order.setThumb1(order.getThumb1().replaceFirst("/Java1_Kmarket1", ""));
			}
		}
		return orders;
	};
	// 나의 전체주문내역 총 갯수
	public int selectOrdersCountTotal(String uid, int sort, String srt, String end) {
		return dao.selectOrdersCountTotal(uid, sort, srt, end);
	};
	
	// 나의문의 불러오기
	public List<ArticleVO> selectQnaArticles(String uid, int start) {
		return dao.selectQnaArticles(uid, start);
	}

	// 나의 문의 총 갯수
	public int selectQnaCountTotal(String uid, String group) {
		return dao.selectQnaCountTotal(uid, group);
	}
	
	// 나의 쿠폰 불러오기
	public List<CouponVO> selectCoupons(String uid){
		return dao.selectCoupons(uid);
	};
	
	// 나의 포인트 내역 불러오기
	public List<PointVO> selectPoints(String uid, int sort, int start){
		return dao.selectPoints(uid, sort, start);
	};
	public List<PointVO> selectPointsDuring(String uid, int start, String srt, String end){
		return dao.selectPointsDuring(uid, start, srt, end);
	};
	
	// 나의 포인트 내역 총 갯수
	public int selectPointsCountTotal(String uid, int sort) {
		return dao.selectPointsCountTotal(uid,  sort);
	};
	public int selectPointsCountDuring(String uid, String srt, String end) {
		return dao.selectPointsCountDuring(uid, srt, end);
	};
	
	// 리뷰 불러오기
	public List<ReviewVO> selectReview(String uid, int start){
		return dao.selectReview(uid, start);
	}
	
	// 나의 리뷰 총 갯수
	public int selectReviewCountTotal(String uid) {
		return dao.selectReviewCountTotal(uid);
	}

	// 나의 정보 수정
	public int updateMemberInfo(MemberVO vo) {
		return dao.updateMemberInfo(vo);
	};
	
	// 나의 정보 비밀번호 수정
	public int updatePassword(String uid, String pass2) {
		String pass = encoder.encode(pass2);
		return dao.updatePassword(uid, pass);
	};
	
	// 나의 정보 회원탈퇴
	public int dropMember(String uid) {
		return dao.dropMember(uid);
	}
	
	// paging
	public int getLastPageNum(int total) {

		int lastPageNum = 0;

		if (total % 10 == 0) {
			lastPageNum = total / 10;
		} else {
			lastPageNum = total / 10 + 1;
		}

		return lastPageNum;
	}

	public int[] getPageGroupNum(int currentPage, int lastPageNum) {
		int currentPageGroup = (int) Math.ceil(currentPage / 10.0);
		int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
		int pageGroupEnd = currentPageGroup * 10;

		if (pageGroupEnd > lastPageNum) {
			pageGroupEnd = lastPageNum;
		}

		int[] result = { pageGroupStart, pageGroupEnd };

		return result;
	}

	public int getPageStartNum(int total, int currentPage) {
		int start = (currentPage - 1) * 10;
		return total - start;
	}

	public int getCurrentPage(String pg) {
		int currentPage = 1;

		if (pg != null) {
			currentPage = Integer.parseInt(pg);
		}

		return currentPage;
	}

	public int getStartNum(int currentPage) {
		return (currentPage - 1) * 10;
	}
}
