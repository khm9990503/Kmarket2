package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;
import kr.co.kmarket2.vo.ReviewVO;

@Mapper
@Repository
public interface ProductDAO {
	// aside
	public List<ProductVO> selectProducts(@Param("cate1") String cate1, @Param("cate2") String cate2, @Param("sort") String sort, @Param("start") int start);
	
	// list
	// 카테고리 및 하위 카테고리 이름 불러오기
	public List<Cate2VO> selectCates(String cate1, String cate2);
	
	// 페이징
	public int selectCountTotal(@Param("cate1") String cate1, @Param("cate2") String cate2);
	
	// view
	// prodNo값으로 상품 정보 불러오기
	public ProductVO selectProduct(String prodNo);
	
	// 댓글 불러오기
	public List<ReviewVO> selectReviews(String prodNo, int start);
	
	// 댓글 페이징을 위해 댓글 전체 불러오기
	public int selectReviewCountTotal(String prodNo);
}
