package kr.co.kmarket2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket2.vo.CartVO;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.OrderItemVO;
import kr.co.kmarket2.vo.OrderVO;
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
	
	// 선택한 상품 정보를 장바구니 테이블에 저장하기
	public int insertCart(CartVO cartVO);
	
	// cart
	// cart 테이블에서 사용자가 담은 상품 불러오기
	public List<CartVO> selectCartByUsername(String username);
	
	// 선택한 상품을 하나씩 삭제하기
	public int deleteCartByProdNo(String prodNo, String username);
	
	// order
	// cartNo값을 이용해서 cart 테이블 값 가져오기
	public List<CartVO> selectCartByCartNo(String[] cartNo);
	
	// 현재 사용자의 정보 가져오기
	public MemberVO selectUserByUsername(String username);
	
	// 주문 정보 DB에 저장하기
	public int insertOrder(OrderVO order);
	
	// 주문 상품 prodNo 이용해서 cart 테이블에서 상품 정보 가져오기
	public CartVO selectCartByProdNo(String prodNo, String username);
	
	// username 이용해서 ordNo값 가져오기
	public OrderVO selectOrdNoByUsername(String username);
	
	// 주문 상품 order_item 테이블에 저장하기
	public int insertOrderItems(OrderItemVO ordItem);
	
	// 주문 이후 사용자의 포인트 변화
	public int updateMemberPoint(int pointSum, String username);
	
	// point 테이블 업데이트하기
	public int insertPoint(String username, int ordNo, int point);
	
	// complete
	// order_item 테이블에서 ordNo값으로 주문완료 상품 가지고오기
	public List<OrderItemVO> selectOrder(String ordNo);
	
	// order 테이블에서 ordNo값으로 주문 정보 가지고 오기
	public OrderVO selectOrderInfo(String ordNo);
	
	// 주문자 정보 가져오기
	public MemberVO selectMemberByUsername(String username);

	// main - 구홍모
	// 메인 상품 목록 구현
	public List<ProductVO> selectMainProducts(String sort);
	// 상품 카테고리1 불러오기
	public List<Cate1VO> selectProdCate1s();
	// 상품 카테고리2 불러오기
	public List<Cate2VO> selectProdCate2s();

}
