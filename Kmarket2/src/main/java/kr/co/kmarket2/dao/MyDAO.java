package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleVO;

@Mapper
@Repository
public interface MyDAO {
	
	// 문의하기 리스트
	public List<ArticleVO> selectQnaArticles(String uid, int start);
	
	// 문의하기 페이징(총 갯수)
	public int selectCountTotal(String uid, String group);
}
