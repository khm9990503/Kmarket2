package kr.co.kmarket2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket2.service.ProductService;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Controller
public class MainController {

	@Autowired
	private ProductService service;
	
	@GetMapping(value= {"","index"})
	public String index(Model model) {
		// 카테고리 목록
		List<Cate1VO> cate1s = service.selectProdCate1s();
		List<Cate2VO> cate2s = service.selectProdCate2s();
		// 상품 목록
		List<ProductVO> bests = service.selectMainProducts("1");
		List<ProductVO> hits = service.selectMainProducts("2");
		List<ProductVO> scores = service.selectMainProducts("3");
		List<ProductVO> lates = service.selectMainProducts("4");
		List<ProductVO> discounts = service.selectMainProducts("5");
		
		model.addAttribute("cate1s",cate1s);
		model.addAttribute("cate2s",cate2s);
		model.addAttribute("bests",bests);
		model.addAttribute("hits",hits);
		model.addAttribute("scores",scores);
		model.addAttribute("lates",lates);
		model.addAttribute("discounts",discounts);
		
		return "index";
	}
}
