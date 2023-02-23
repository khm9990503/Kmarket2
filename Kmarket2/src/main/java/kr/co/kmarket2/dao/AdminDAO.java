package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.ArticleCateVO;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Mapper
@Repository
public interface AdminDAO {
/////// 관리자 상품 관련 /////////////////////////////////////////////
	public List<Cate1VO> selectCate1 ();
	public List<Cate2VO> selectCate2 (int cate1);
	public int insertProduct (ProductVO vo);
	public int selectCountTotal ();
	public int selectCountTotalSeller (String seller);
	public int selectCountTotalSearch (String search, String type);
	public int selectCountTotalSearchSeller (String seller, String search, String type);
	public List<ProductVO> selectProducts(int start);
	public List<ProductVO> selectProductsSeller(int start);
	public List<ProductVO> searchProducts(String search,@Param("type") String type, int start);
	public List<ProductVO> searchProductsSeller(String seller,String search,@Param("type") String type, int start);
	public int updateProduct (ProductVO vo);
	public int deleteProduct (String prodNo);
/////// 관리자 고객센터 관련 /////////////////////////////////////////////
	public List<ArticleCateVO> selectArticeCates ();
	public List<ArticleCateVO> selectArticeCatesByCate (String cate);
	public int selectCountTotalArticle (String group, String cate, String cate2 , @Param("type")String type);
	public List<ArticleVO> selectArticlesByGroup (String group, String cate, String cate2, int start,@Param("type")String type);
	public ArticleVO selectArticle (String no);
	public ArticleVO selectReply (String parent);
	public int insertArticle (ArticleVO vo);
	public int insertReply (String parent, String uid, String content, String regip);
	public int updateArticle (ArticleVO vo);
	public int updateComment (String no);
	public int deleteArticles (String chk);
}
