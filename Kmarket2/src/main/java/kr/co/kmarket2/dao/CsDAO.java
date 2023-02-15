package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleVO;

@Mapper
@Repository
public interface CsDAO {
	
	// notice list
	public List<ArticleVO> selectArticles(String group, int start);
	
	public List<ArticleVO> selectCateArticles(String group, String cate, int start);
	
	
	// list paging
	public int selectCountTotal(String group);
	
	// 카테고리별 리스트 페이징
	public int selectCountCateTotal(String group, String cate);
	
	// view
	public ArticleVO selectArticle(int no);
	
	
	//faq list
	public List<ArticleVO> selectFaqArticles(String group, String cate);
	
	//faq category
	public List<ArticleVO> selectFaqCates(String cate);
	
	//index notice list
	public List<ArticleVO> selectNotices(String group);
	
	
	

}
