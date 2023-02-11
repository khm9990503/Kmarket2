package kr.co.kmarket2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.ProductDAO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	// aside
	public List<ProductVO> selectProducts(String cate1, String cate2, String sort, int start){
		return dao.selectProducts(cate1, cate2, sort, start);
	}
	
	// list
	public List<Cate2VO> selectCates(String cate1, String cate2) {
		return dao.selectCates(cate1, cate2);
	}
	
	
	
	// 페이징
	// 글 총 갯수(total)
	public int selectCountTotal(String cate1, String cate2) {
		return dao.selectCountTotal(cate1, cate2);
	}
	
	// 현재 페이지 번호
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null)
			currentPage = Integer.parseInt(pg);
		
		return currentPage;
	}
	
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage -1 ) * 10;
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
		int lastPageNum = 0;
		
		if(total % 10 == 0)
			lastPageNum = total /10;
		else
			lastPageNum = total /10 + 1;
		
		return lastPageNum;
	}
	
	// 페이지 시작 번호
	public int getPageStartNum(int total, int start) {
		return total - start;
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {
		int groupCurrent = (int) Math.ceil(currentPage/10.0);
		int groupStart = (groupCurrent - 1)*10 + 1;
		int groupEnd = groupCurrent * 10;
		
		if(groupEnd > lastPageNum)
			groupEnd = lastPageNum;
		
		int[] groups = {groupStart, groupEnd};
		return groups;
	}
}
