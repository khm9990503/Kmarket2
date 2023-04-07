package kr.co.kmarket2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.MyDAO;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.OrderVO;
import kr.co.kmarket2.vo.ReviewVO;

@Service
public class MyService {

	@Autowired
	private MyDAO dao;
	
	// 마이페이지 메인 주문 목록
	public List<OrderVO> selectOrdersIndex(String uid){
		return dao.selectOrdersIndex(uid);
	};
	
	// 나의문의 불러오기
	public List<ArticleVO> selectQnaArticles(String uid, int start) {
		return dao.selectQnaArticles(uid, start);
	}

	// 나의 문의 총 갯수
	public int selectQnaCountTotal(String uid, String group) {
		return dao.selectQnaCountTotal(uid, group);
	}
	
	// 리뷰 불러오기
	public List<ReviewVO> selectReview(String uid, int start){
		return dao.selectReview(uid, start);
	}
	
	// 나의 리뷰 총 갯수
	public int selectReviewCountTotal(String uid) {
		return dao.selectReviewCountTotal(uid);
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
