package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

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
}
