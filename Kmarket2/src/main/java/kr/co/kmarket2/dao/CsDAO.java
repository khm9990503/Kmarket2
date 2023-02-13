package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleVO;

@Mapper
@Repository
public interface CsDAO {
	
	public List<ArticleVO> selectArticles(String group, int start);
	
	public List<ArticleVO> selectCateArticles(String group, String cate, int start);
	
	// 리스트 페이징
	public int selectCountTotal(String group);
	
	// 카테고리별 리스트 페이징
	public int selectCountCateTotal(String group, String cate);
	
	public ArticleVO selectArticle(int no);
}