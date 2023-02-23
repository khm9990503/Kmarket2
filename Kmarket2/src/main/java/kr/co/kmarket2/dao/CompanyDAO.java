package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleVO;

@Mapper
@Repository
public interface CompanyDAO {

	// notice list 출력
	public List<ArticleVO> selectComArticles(String group, int start);
	
	// notice list 페이징
	public int selectCountTotal(String group);
}
