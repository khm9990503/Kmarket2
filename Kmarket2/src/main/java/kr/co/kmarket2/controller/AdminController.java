package kr.co.kmarket2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket2.service.AdminService;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@GetMapping("admin/index")
	public String index() {
		return "admin/index";
	}
	@GetMapping("admin/product/list")
	public String list() {
		return "admin/product/list";
	}
	@GetMapping("admin/product/register")
	public String register(Model model) {
		List<Cate1VO> cate1s = service.selectCate1();
		model.addAttribute("cate1s",cate1s);
		return "admin/product/register";
	}
	@ResponseBody
	@GetMapping("admin/product/cate2List")
	public Map<String, List<Cate2VO>> cate2List(Model model, int cate1) {
		List<Cate2VO> cate2s = service.selectCate2(cate1);
		Map<String, List<Cate2VO>> result = new HashMap<>();
		result.put("cate2s", cate2s);
		return result;
	}
	@PostMapping("admin/product/register")
	public String register(ProductVO vo, HttpServletRequest req) {
		vo.setIp(req.getRemoteAddr()); 
		int result = service.insertProduct(vo);
		return "redirect:/admin/product/list";
	}
}
