package kr.co.kmarket2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmarket2.service.CsService;
import kr.co.kmarket2.vo.ArticleCateVO;
import kr.co.kmarket2.vo.ArticleVO;
import kr.co.kmarket2.vo.QnaCateVO;

@Controller
public class CsController {

	@Autowired
	private CsService service;
	
	
	
	@GetMapping("cs/index")
	public String csIndex(Model model, String group) {
		
		// 공지사항 목록 가져오기
		List<ArticleVO> articles = service.selectNotices(group);
		
		// 문의하기 목록 가져오기
		List<ArticleVO> qnaArticles = service.selectQnas(group);
		
		model.addAttribute("qnaArticles",qnaArticles);
		model.addAttribute("articles", articles);
		
		return "cs/index";
	}
	
	
    @GetMapping("cs/notice/list")
    public String noticeList(Model model,String group, String cate, String pg) {
    	
    	//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		if(cate == null) {
				total = service.selectCountTotal(group); //전체 게시물 갯수
		}else {
				total = service.selectCountCateTotal(group, cate); //카테고리별 게시물 갯수
			}
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
    			
		//전체 목록 가져오기
		List<ArticleVO> articles = service.selectArticles(group, start);
		
    	//카테고리별 목록 가져오기
    	List<ArticleVO> article = service.selectCateArticles(group ,cate ,start);
    	
    	model.addAttribute("cate",cate);
    	model.addAttribute("articles", articles);
    	model.addAttribute("article", article);
    	
    	
        return "cs/notice/list";
    }

    @GetMapping("cs/notice/view")
    public String noticeView(int no, String cate, Model model) {
    	
    	// 해당 글 가져오기
    	ArticleVO article = service.selectArticle(no);
    	
    	model.addAttribute("article", article);
    	model.addAttribute("cate",cate);
    	
        return "cs/notice/view";
    }

    @GetMapping("cs/faq/list")
    public String faqList(Model model, String cate, String group) {
    	
    	//카테고리 가져오기
    	List<ArticleVO> cates = service.selectCates(cate);
    	
    	//목록 가져오기
    	List<ArticleVO> articles = service.selectFaqArticles(group, cate);
    	
    	model.addAttribute("cates", cates);
    	model.addAttribute("articles", articles);
    	model.addAttribute("cate", cate);
        return "cs/faq/list";
    }

    @GetMapping("cs/faq/view")
    public String faqView(Model model, String cate, String group, int no) {
    	
    	// 해당 글 가져오기
    	ArticleVO article = service.selectArticle(no);
    	
    	model.addAttribute("article", article);
    	model.addAttribute("cate", cate);
        return "cs/faq/view";
    }

    @GetMapping("cs/qna/list")
    public String qnaList(Model model,String group, String cate, String pg) {
    	
    	//페이징 
    	int currentPage = service.getCurrentPage(pg); // 현재 페이지 번호
		int total = 0;
		
		
		total = service.selectCountCateTotal(group, cate); //카테고리별 게시물 갯수
		
		
		int lastPageNum = service.getLastPageNum(total);// 마지막 페이지 번호
		int[] result = service.getPageGroupNum(currentPage, lastPageNum); // 페이지 그룹번호
		int pageStartNum = service.getPageStartNum(total, currentPage); // 페이지 시작번호
		int start = service.getStartNum(currentPage); // 시작 인덱스
		
		model.addAttribute("lastPageNum", lastPageNum);		
		model.addAttribute("currentPage", currentPage);		
		model.addAttribute("pageGroupStart", result[0]);
		model.addAttribute("pageGroupEnd", result[1]);
		model.addAttribute("pageStartNum", pageStartNum+1);
    			
		
		
    	
    	//카테고리별 목록 가져오기
    	List<ArticleVO> articles = service.selectQnaArticles(group,cate,start);
    	
    	model.addAttribute("cate",cate);
    	
    	model.addAttribute("articles", articles);
    	
        return "cs/qna/list";
    }

    @GetMapping("cs/qna/view")
    public String qnaView(Model model, String cate, int no) {
    	
    	ArticleVO article = service.selectQnaArticle(no);
    	
    	ArticleVO answer = service.selectAnswer(no);
    	
    	model.addAttribute("answer",answer);
    	model.addAttribute("article",article);
    	model.addAttribute("cate", cate);
        return "cs/qna/view";
    }

    @GetMapping("cs/qna/write")
    public String qnaWrite(Model model, String cate) {
    	
    	model.addAttribute("cate",cate);
    	
        return "cs/qna/write";
    }
    
    @PostMapping("cs/qna/write")
    public String qnaWrite(Model model, ArticleVO vo, HttpServletRequest req) {
    	
    	vo.setRegip(req.getRemoteAddr());
    	
    	// 글 쓰기
    	service.insertArticle(vo);
    	
    	return "redirect:/cs/qna/list?group=qna&cate="+vo.getCate();
    }
    
    
   
}