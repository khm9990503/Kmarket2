package kr.co.kmarket2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.CompanyDAO;
import kr.co.kmarket2.vo.ArticleVO;

@Service
public class CompanyService {

	@Autowired
	private CompanyDAO dao;
	
	// company notice list
	
	public List<ArticleVO> selectComArticles(String group, int start){
		return dao.selectComArticles(group, start);
	}
	
	
	
	
	
	// notice list 페이징
	
	public int selectCountTotal(String group) {
		return dao.selectCountTotal(group);
	}
	
	
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
