package kr.co.kmarket2.controller;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket2.service.ProductService;
import kr.co.kmarket2.vo.CartVO;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.MemberVO;
import kr.co.kmarket2.vo.OrderItemVO;
import kr.co.kmarket2.vo.OrderVO;
import kr.co.kmarket2.vo.ProductVO;
import kr.co.kmarket2.vo.ReviewVO;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private PasswordEncoder encoder; // 회원가입 기능 구현 전 임의로 아이디, 비밀번호 지정하기 위해 주입
	
	@GetMapping("product/list")
	public String list(String cate1, String cate2, String sort, Model model, @RequestParam(value="pg", defaultValue = "1") String pg) {
		// 페이징 처리
		int total = service.selectCountTotal(cate1, cate2);
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage, 10);
		int lastPageNum = service.getLastPageNum(total, 10);
		int startPageNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum, 10);
		
		model.addAttribute("groups", groups);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("lastPageNum", lastPageNum);
	    model.addAttribute("startPageNum", startPageNum);
		
		// 상품 정보 불러오기
		List<ProductVO> products = service.selectProducts(cate1, cate2, sort, start);
		// 할인된 가격 적용
		for(ProductVO product: products) {
			product.setDisPrice((int) Math.ceil(product.getPrice() * (1 - product.getDiscount() * 0.01)));
		}
		
		// 할인된 가격으로 sort(가격높은순, 가격낮은순)
		if(sort.equals(String.valueOf(2)))
			Collections.sort(products, (p1, p2) -> p1.getDisPrice() - p2.getDisPrice());
		else if(sort.equals(String.valueOf(3)))
			Collections.sort(products, (p1, p2) -> p2.getDisPrice() - p1.getDisPrice());
		
		model.addAttribute("products", products);
		model.addAttribute("cate1", cate1);
		model.addAttribute("cate2", cate2);
		model.addAttribute("sort", sort);
		
		// cate1, cate2 값에 따라 카테고리 및 하위 카테고리 이름 불러오기
		// 파라미터로 받아온 cate1, cate2값(실질적으로 int)을 이용해 DB에서 카테고리 및 하위카테고리 이름을 가져오기
		List<Cate2VO> cates = service.selectCates(cate1, cate2);
		model.addAttribute("cates", cates.get(0));
		
		// 카테고리 목록 불러오기 - 구홍모 02/24
		List<Cate1VO> cate1s = service.selectProdCate1s();
		List<Cate2VO> cate2s = service.selectProdCate2s();
		model.addAttribute("cate1s",cate1s);
		model.addAttribute("cate2s",cate2s);
		return "product/list";
	}
	
	@GetMapping("product/view")
	public String view(String prodNo, String cate1, String cate2, Model model, @RequestParam(value="pg", defaultValue = "1") String pg) {
		// cate1, cate2 값에 따라 카테고리 및 하위 카테고리 이름 불러오기(list() 참조)
		List<Cate2VO> cates = service.selectCates(cate1, cate2);
		model.addAttribute("cates", cates.get(0));
		model.addAttribute("cate1", cate1);
		model.addAttribute("cate2", cate2);
		
		// 파라미터로 받아온 prodNo값을 이용해 상품정보 DB에서 불러오기
		ProductVO product = service.selectProduct(prodNo);
		model.addAttribute("product", product);
		// 할인된 가격 적용
		product.setDisPrice((int) Math.ceil(product.getPrice() * (1 - product.getDiscount() * 0.01)));
		
		// 배송 예정 날짜 구하기
		String[] estimatedDeliveryInfo = service.getEstimatedDeliveryDate();
		model.addAttribute("estimatedDeliveryInfo", estimatedDeliveryInfo);
		
		// 댓글 페이징 처리
		// 페이징 처리
		int total = service.selectReviewCountTotal(prodNo);
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage, 5);
		int lastPageNum = service.getLastPageNum(total, 5);
		int startPageNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum, 5);
		
		model.addAttribute("groups", groups);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("lastPageNum", lastPageNum);
	    model.addAttribute("startPageNum", startPageNum);
		
		// 댓글 불러오기(페이지 최초 로드시)
		List<ReviewVO> reviews = service.selectReviews(prodNo, start);
		
		// 댓글 작성자명 마스킹 처리하기(스크립트로도 가능하지만 사용자가 마스킹 전의 오리지널 데이터에 접근할 수도 있음; https://stackoverflow.com/questions/14495290/replace-last-5-character-from-username-with-x)
		for(ReviewVO review : reviews) {
			String s = "*";
			for(int i = 0; i <review.getUid().length() - 4; i++)
				s += "*";
			
			// 맨 처음 셋째 자리까지를 제외하고 나머지 문자는 모두 마스킹 처리
			review.setUid(review.getUid().replaceAll("(?<=^...)(.*)", s));
		}
		model.addAttribute("reviews", reviews);
		
		// 카테고리 목록 불러오기 - 구홍모 02/24
		List<Cate1VO> cate1s = service.selectProdCate1s();
		List<Cate2VO> cate2s = service.selectProdCate2s();
		model.addAttribute("cate1s",cate1s);
		model.addAttribute("cate2s",cate2s);
		
		return "product/view";
	}
	
	@ResponseBody
	@GetMapping("product/retrieveReviews")
	public Map<String, List<ReviewVO>> retrieveReviews(String prodNo, String pg){
		// 페이징 처리
		int total = service.selectReviewCountTotal(prodNo);
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage, 5);
		int lastPageNum = service.getLastPageNum(total, 5);
		int startPageNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum, 5);
		
		// 댓글 불러오기(ajax)
		List<ReviewVO> reviews = service.selectReviews(prodNo, start);
		System.out.println(reviews);
		// 페이징 처리를 위해 페이지 관련 정보 vo 객체에 저장하기
		for(ReviewVO review: reviews) {
			review.setGroupStart(groups[0]);
			review.setGroupEnd(groups[1]);
			review.setLastPageNum(lastPageNum);
			review.setCurrentPage(currentPage);
		}
		
		// 댓글 작성자명 마스킹 처리하기(스크립트로도 가능하지만 사용자가 마스킹 전의 오리지널 데이터에 접근할 수도 있음; https://stackoverflow.com/questions/14495290/replace-last-5-character-from-username-with-x)
		for(ReviewVO review : reviews) {
			String s = "*";
			for(int i = 0; i <review.getUid().length() - 4; i++)
				s += "*";
			
			// 맨 처음 셋째 자리까지를 제외하고 나머지 문자는 모두 마스킹 처리
			review.setUid(review.getUid().replaceAll("(?<=^...)(.*)", s));
		}
		
		// 뷰로 ajax 처리값 전송하기
		Map<String, List<ReviewVO>> result = new HashMap<>();
		result.put("reviews", reviews);
		return result;
	}
	
	@PostMapping("product/putInCart")
	@ResponseBody
	public Map<String, Object> putInCart(@RequestBody CartVO cartVO , Principal principal) {
		// 현재 사용자 정보 가져오기
		String username = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		if(principal != null){ 
			username = principal.getName();
			
			cartVO.setUid(username);
			
			// cart 테이블에 정보 저장
			int result = service.insertCart(cartVO);
			resultMap.put("result", result);
			resultMap.put("username", cartVO.getUid()); // 로그인 기능 구현 때까지 임시로 사용
			return resultMap;
		}else{
			resultMap.put("result", 0);
			return resultMap;
		}
		
		//String username = "a123123";
		//Map<String, Object> resultMap = new HashMap<>();
	}
	
	@GetMapping("product/cart")
	public String cart(String uid, Model model) {
		// 현재 사용자 정보 가져와서 cart 테이블에서 상품 가져오기
		/*
		String username = ((UserDetails) principal).getUserName();
		*/
		
		List<CartVO> items = service.selectCartByUsername(uid);
		
		model.addAttribute("items", items);
		
		// 카테고리 목록 불러오기 - 구홍모 02/24
		List<Cate1VO> cate1s = service.selectProdCate1s();
		List<Cate2VO> cate2s = service.selectProdCate2s();
		model.addAttribute("cate1s",cate1s);
		model.addAttribute("cate2s",cate2s);
		return "product/cart";
	}
	
	@ResponseBody
	@GetMapping("product/remove")
	public Map<String, String> removeFromCart(String[] items, Principal principal) {
		// 현재 사용자 username과 prodNo 이용해서 cart 테이블에서 선택한 상품 삭제
		// 혹시 principal 객체 인식 못하면 뷰 페이지에서 sec:authentication으로 username보내기
		String username = principal.getName();
		
		for(int i =0; i < items.length; i++) {
			service.deleteCartByProdNo(items[i], username);
		}
		
		// uid ajax reponse값으로 설정 => ajax 성공하면 리다이렉트하는데 uid가 파라미터로 필요
		Map<String, String> result = new HashMap<>();
		result.put("username", username);
		return result;
	}
	
	@PostMapping("product/order")
	public String order(@RequestParam(value="individualItem") String[] individualItem, Model model, Principal principal){
		// cart 페이지에서 주문하기 누를 경우(폼 submit) 체크된 체크박스에 바인딩된 cartNo값 전송한다
		List<CartVO> orderList = service.selectCartByCartNo(individualItem);
		
		for(CartVO item : orderList)
			item.setDisPrice((int) Math.ceil(item.getPrice() * item.getDiscount() * 0.01));
		
		// 현재 사용자 정보 불러오기(포인트값)
		String username = principal.getName();
		MemberVO currentUser = service.selectUserMyUsername(username);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("user", currentUser);
		return "product/order";
	}
	
	@PostMapping("product/storeOrderInfo")
	@ResponseBody
	public Map<String, Integer> storeOrderInfo(@RequestBody OrderVO order){
		int result = service.insertOrder(order);
		
		// (현재 사용자의 포인트) + savePoint - usedPoint
		int pointSum = order.getSavePoint() - order.getUsedPoint();
		service.updateMemberPoint(pointSum, order.getOrdUid());
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		return resultMap;
	}
	
	@PostMapping("product/storeOrderItems")
	@ResponseBody
	public Map<String, Integer> storeOrderItems(@RequestBody Map<String, Object> prodNoListToSend){
		ArrayList<String> prodNos = (ArrayList<String>) prodNoListToSend.get("prodNoList");
		String username = (String) prodNoListToSend.get("uid");
		
		// username을 이용해서 order 테이블에서 ordNo값 가져오기
			OrderVO order = service.selectOrdNoByUsername(username);
			int ordNo = order.getOrdNo();
			int point = order.getSavePoint();
		
		// prodNo와 username을 이용해서 cart 테이블에서 상품정보 가져오기
		List<OrderItemVO> ordItems = new ArrayList<>();
		List<String> cartProdNos = new ArrayList<>(); // ord_item 테이블에 추가 끝나고 cart 테이블에서 삭제를 위해 저장
		for(int i =0 ; i < prodNos.size(); i++){
			CartVO item = service.selectCartByProdNo(prodNos.get(i), username);
			
			OrderItemVO ordItem = new OrderItemVO();
			ordItem.setOrdNo(ordNo);
			ordItem.setProdNo(item.getProdNo());
			ordItem.setCount(item.getCount());
			ordItem.setPrice(item.getPrice());
			ordItem.setDiscount(item.getDiscount());
			ordItem.setPoint(item.getPoint());
			ordItem.setDelivery(item.getDelivery());
			ordItem.setTotal(item.getTotal());
			
			ordItems.add(ordItem);
			
			cartProdNos.add(String.valueOf(item.getProdNo()));
		}
		
		int ordResult = 0;
		// ordNo값과 상품정보를 order_item 테이블에 저장
		for(OrderItemVO ordItem : ordItems) {
			ordResult = service.insertOrderItems(ordItem);
			
			if(ordResult == 0)
				break;
		}
		
		// 주문 완료된 상품은 cart 테이블에서 삭제하기
		for(String cartProdNo : cartProdNos)
			service.deleteCartByProdNo(cartProdNo, username);
		
		// point 테이블 업데이트하기
		service.insertPoint(username, ordNo, point);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("ordResult", ordResult);
		resultMap.put("ordNo", ordNo);
		return resultMap;
	}
	
	@GetMapping("product/complete")
	public String complete(String ordNo, Model model) {
		// order_item 테이블에서 ordNo값으로 주문완료 상품 가지고오기
		List<OrderItemVO> ordItems = service.selectOrder(ordNo);
		
		// order 테이블에서 ordNo값으로 주문 정보 가지고 오기
		OrderVO order = service.selectOrderInfo(ordNo);
		
		// order_item의 할인 가격 구하기
		for(OrderItemVO item : ordItems) {
			item.setDiscountPrice((int) Math.floor(item.getPrice() * item.getDiscount() * 0.01));
		}
		
		// 주문자 정보 저장
		MemberVO orderer = service.selectMemberByUsername(order.getOrdUid());
		
		model.addAttribute("ordItems", ordItems);
		model.addAttribute("order", order);
		model.addAttribute("orderer", orderer);
		
		
		// 카테고리 목록 불러오기 - 구홍모 02/24
		List<Cate1VO> cate1s = service.selectProdCate1s();
		List<Cate2VO> cate2s = service.selectProdCate2s();
		model.addAttribute("cate1s",cate1s);
		model.addAttribute("cate2s",cate2s);
		return "product/complete";
	}
}
