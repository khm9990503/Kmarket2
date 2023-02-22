package kr.co.kmarket2.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.ProductDAO;
import kr.co.kmarket2.vo.CartVO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.OrderVO;
import kr.co.kmarket2.vo.ProductVO;
import kr.co.kmarket2.vo.ReviewVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	// aside
	public List<ProductVO> selectProducts(String cate1, String cate2, String sort, int start){
		return dao.selectProducts(cate1, cate2, sort, start);
	}
	
	// list
	// 카테고리 및 하위 카테고리 이름 불러오기
	public List<Cate2VO> selectCates(String cate1, String cate2) {
		return dao.selectCates(cate1, cate2);
	}
	
	// view
	// prodNo값으로 상품 정보 불러오기
	public ProductVO selectProduct(String prodNo) {
		return dao.selectProduct(prodNo);
	}
	
	// 배송 예정일 구하기(현재 날짜 + 3 영업일)
	// https://stackoverflow.com/questions/24114155/how-to-get-current-date-and-add-five-working-days-in-java
	public String[] getEstimatedDeliveryDate() {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(2023, Calendar.FEBRUARY, 23); 테스트 용 임의 날짜 지정
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
		String estimatedDeliveryTime = null;
		
		// 현재 날짜에 3 영업일만큼 더하기(토, 일 제외)
		int workingDays = 3;
		int totalDays= 0;
		for(int i = 0; i< workingDays; ) {
			totalDays++;
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			
			// 토, 일이 아닐 때만 카운트 올라감(Calendar class: 일요일 1 - 토요일 7)
			if(calendar.get(Calendar.DAY_OF_WEEK)>=2 && calendar.get(Calendar.DAY_OF_WEEK)<7)
				i++;
		}
		
		String estimatedDeliveryDate = formatter.format(calendar.getTime());
		
		// 해당 배송 예정 날짜의 요일 구하기
		DateFormat weekOfDayFormatter = new SimpleDateFormat("EEEE", Locale.KOREAN);
		String estimatedWeekOfDay = weekOfDayFormatter.format(calendar.getTime());
		
		// 영업일 제외한 배송 기간 구하기
		switch(totalDays) {
			case 5:
				estimatedDeliveryTime = null;
				break;
			case 4:
				estimatedDeliveryTime = "그글피";
				break;
			case 3:
				estimatedDeliveryTime = "글피";
				break;
		}
		
		String[] estimatedDateInfo = {estimatedDeliveryDate, estimatedWeekOfDay.substring(0,1), estimatedDeliveryTime};
		return estimatedDateInfo;
	}
	
	// 댓글 불러오기
	public List<ReviewVO> selectReviews(String prodNo, int start){
		return dao.selectReviews(prodNo, start);
	}
	
	// 선택한 상품 정보를 장바구니 테이블에 저장하기
	public int insertCart(CartVO cartVO) {
		return dao.insertCart(cartVO);
	}
	
	// cart
	// cart 테이블에서 사용자가 담은 상품 불러오기
	public List<CartVO> selectCartByUsername(String username){
		return dao.selectCartByUsername(username);
	}
	
	// 선택한 상품을 하나씩 삭제하기
	public int deleteCartByProdNo(String prodNo, String username) {
		return dao.deleteCartByProdNo(prodNo, username);
	}
	
	// order
	// cartNo값을 이용해서 cart 테이블 값 가져오기
	public List<CartVO> selectCartByCartNo(String[] cartNo){
		return dao.selectCartByCartNo(cartNo);
	}
	
	// 현재 사용자의 정보 가져오기
	public MemberVO selectUserMyUsername(String username) {
		return dao.selectUserByUsername(username);
	}
	
	// 주문 정보 DB에 저장하기
	public int insertOrder(OrderVO order) {
		return dao.insertOrder(order);
	}
	
	
	// 페이징
	// 글 총 갯수(total)
	public int selectCountTotal(String cate1, String cate2) {
		return dao.selectCountTotal(cate1, cate2);
	}
	
	// 댓글 총 갯수(total)
	public int selectReviewCountTotal(String prodNo) {
		return dao.selectReviewCountTotal(prodNo);
	}
	
	// 현재 페이지 번호
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null)
			currentPage = Integer.parseInt(pg);
		
		return currentPage;
	}
	
	// 페이지 시작값
	public int getLimitStart(int currentPage, int articlesPerPage) {
		return (currentPage -1 ) * articlesPerPage;
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(int total, int articlesPerPage) {
		int lastPageNum = 0;
		
		if(total % articlesPerPage == 0)
			lastPageNum = total /articlesPerPage;
		else
			lastPageNum = total /articlesPerPage + 1;
		
		return lastPageNum;
	}
	
	// 페이지 시작 번호
	public int getPageStartNum(int total, int start) {
		return total - start;
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum, int articlesPerPage) {
		int groupCurrent = (int) Math.ceil(currentPage/Double.valueOf(articlesPerPage));
		int groupStart = (groupCurrent - 1)*articlesPerPage + 1;
		int groupEnd = groupCurrent * articlesPerPage;
		
		if(groupEnd > lastPageNum)
			groupEnd = lastPageNum;
		
		int[] groups = {groupStart, groupEnd};
		return groups;
	}
}
