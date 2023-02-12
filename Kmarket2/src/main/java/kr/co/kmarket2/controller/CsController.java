package kr.co.kmarket2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmarket2.service.CsService;

@Controller
public class CsController {

	
	private CsService service;
	
	
    @GetMapping("cs/notice/list")
    public String noticeList(Model model, String cate) {
    	
    	model.addAttribute("cate",cate);
    	
    	
        return "cs/notice/list";
    }

    @GetMapping("cs/notice/view")
    public String noticeView() {
        return "cs/notice/view";
    }

    @GetMapping("cs/faq/list")
    public String faqList() {
        return "cs/faq/list";
    }

    @GetMapping("cs/faq/view")
    public String faqView() {
        return "cs/faq/view";
    }

    @GetMapping("cs/qna/list")
    public String qnaList() {
        return "cs/qna/list";
    }

    @GetMapping("cs/qna/view")
    public String qnaView() {
        return "cs/qna/view";
    }

    @GetMapping("cs/qna/write")
    public String qnaWrite() {
        return "cs/qna/write";
    }
}
