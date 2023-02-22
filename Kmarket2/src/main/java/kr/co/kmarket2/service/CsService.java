package kr.co.kmarket2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket2.dao.CsDAO;
import kr.co.kmarket2.vo.ArticleCateVO;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.QnaCateVO;

@Service
public class CsService {
	
	@Autowired
	private CsDAO dao;
	
	
	/* 공지사항 리스트 */
	public List<ArticleVO> selectArticles(String group, int start){
		return dao.selectArticles(group ,start);
	}
	
	public List<ArticleVO> selectCateArticles(String group, String cate, int start){
		return dao.selectCateArticles(group, cate, start);
	}
	
	// index 공지사항 리스트
	public List<ArticleVO> selectNotices(String group){
		return dao.selectNotices(group);
	}
	
	// index 문의하기 리스트
	public List<ArticleVO> selectQnas(String group){
		return dao.selectQnas(group);
	}
	
	
	
	// 공지사항 리스트 페이징작업
	public int selectCountTotal(String group) {
		return dao.selectCountTotal(group);
	}
		
	// 공지사항 리스트 카테고리별 페이징 작업
	public int selectCountCateTotal(String group, String cate) {
		return dao.selectCountCateTotal(group, cate);
	}
	
	// 자주묻는질문 리스트
	public List<ArticleVO> selectFaqArticles(String group, String cate) {
		return dao.selectFaqArticles(group, cate);
	}
	
	public List<ArticleVO> selectCates(String cate){
		return dao.selectCates(cate);
	}
	
	// 문의하기 리스트
	public List<ArticleVO> selectQnaArticles(String group, String cate, int start){
		return dao.selectQnaArticles(group, cate, start);
	}
	
	
	/* View */
	
	public ArticleVO selectArticle(int no) {
		return dao.selectArticle(no);
	}
	
	public ArticleVO selectQnaArticle(int no) {
		return dao.selectQnaArticle(no);
	}
	
	public ArticleVO selectAnswer(int no) {
		return dao.selectAnswer(no);
	}
	
	
	/* write */
	
	public int insertArticle(ArticleVO vo) {
		return dao.insertArticle(vo);
	}
	
	
	
	// 페이징
		public int getLastPageNum(int total) {
			
			int lastPageNum = 0;
			
			if(total % 10 == 0){
				lastPageNum = total / 10;
			}else{
				lastPageNum = total / 10 + 1;
			}
			
			return lastPageNum;
		}
		
		public int[] getPageGroupNum(int currentPage, int lastPageNum) {
			int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
			int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
			int pageGroupEnd = currentPageGroup * 10;
			
			if(pageGroupEnd > lastPageNum){
				pageGroupEnd = lastPageNum;
			}
			
			int[] result = {pageGroupStart, pageGroupEnd};
			
			return result;
		}
		
		public int getPageStartNum(int total, int currentPage) {
			int start = (currentPage - 1) * 10;
			return total - start;
		}
		
		public int getCurrentPage(String pg) {
			int currentPage = 1;
			
			if(pg != null){
				currentPage = Integer.parseInt(pg);	
			}
			
			return currentPage;
		}
		
		public int getStartNum(int currentPage) {
			return (currentPage - 1) * 10;
		}
	
}
