package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleCateVO;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.QnaCateVO;

@Mapper
@Repository
public interface CsDAO {
	
	// notice list
	public List<ArticleVO> selectArticles(String group, int start);
	
	public List<ArticleVO> selectCateArticles(String group, String cate, int start);
	
	
	// list paging
	public int selectCountTotal(String group);
	
	// 카테고리별 list paging
	public int selectCountCateTotal(String group, String cate);
	
	// view
	public ArticleVO selectArticle(int no);
	
	
	//faq list
	public List<ArticleVO> selectFaqArticles(String group, String cate);
	
	//category
	public List<ArticleVO> selectCates(String cate);
	
	//qna list
	public List<ArticleVO> selectQnaArticles(String group, String cate, int start);
	
	//qna view
	public ArticleVO selectQnaArticle(int no);
	
	public ArticleVO selectAnswer(int no);
	
	//qna write
	public int insertArticle(ArticleVO vo);
	
	
	
	//index notice list
	public List<ArticleVO> selectNotices(String group);
	
	//index qna list
	public List<ArticleVO> selectQnas(String group);
	
	
	

}
