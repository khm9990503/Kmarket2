package kr.co.kmarket2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.kmarket2.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

	private final EmailService emailService;

	
    @ResponseBody
    @GetMapping("member/emailAuth")
    public Map<String, String> mailConfirm(@RequestParam String email) throws Exception {
    	List<String> result = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + result.get(0));
        Map<String, String> map = new HashMap<>();
        map.put("code", result.get(0));
        map.put("status", result.get(1));
        return map;
    }
}
