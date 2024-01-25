package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Handler.PagingHandler;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService bsv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardVO bvo) {
		int isOk = bsv.register(bvo);
		return "/index";
	}
	@GetMapping("/list")
	public void list(Model m,PagingVO pgvo) {
		log.info(">>>>> pgvo >>>>{}",pgvo);
		
		//totalcount
		int totalCount = bsv.getTotalCount(pgvo);
		//pagingHandler 객체 생성 
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		List<BoardVO> list = bsv.getList(pgvo);
		
		//pagingHandler 객체 보내기
		
		log.info(">>>>> list >>>>{}",list);
		m.addAttribute("list", list);
		m.addAttribute("ph",ph);
	}
	@GetMapping({"/detail","/modify"})
	public void detail(Model m,@RequestParam("bno") long bno) {
		m.addAttribute("bvo",bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo,RedirectAttributes re) {
		
		log.info(">>>> bvo>>>" +bvo);
		int isOk = bsv.modify(bvo);
		re.addAttribute("bno", bvo.getBno());
		// 리다이렉트에 ?로 값ㄱ을 줘서 bno가 두번 붙음 / re.addFlashAttribute("bno",bvo.getBno())-로 하면 디테일페이지로 갈때 오류 생김
		return "redirect:/board/detail";
	}
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") long bno) {
		bsv.remove(bno);
		return "redirect:/board/list";
	}
	
	
}
