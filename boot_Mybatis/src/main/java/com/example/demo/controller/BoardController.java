package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Handler.FileHandler;
import com.example.demo.Handler.PagingHandler;
import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
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
	private final FileHandler fh;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardVO bvo,@RequestParam(name="files",required = false)MultipartFile[] files) {
		//파일 업로드 부분 추가
		List<FileVO> flist = null;
		if(files[0].getSize() > 0 || files != null) {
			//파일 핸들러 작업
			flist = fh.uploadFiles(files);
			
		}
		//
		bsv.register(new BoardDTO(bvo,flist));
		return "redirect:/board/list";
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
		m.addAttribute("bdto",bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo,RedirectAttributes re,@RequestParam(name="files",required = false)MultipartFile[]files) {
		
		log.info(">>>> bvo>>>" +bvo);
		List<FileVO> flist=null;
		if(files[0].getSize()>0 || files != null) {
			 flist= fh.uploadFiles(files);
		}
		
		bsv.modify(new BoardDTO(bvo,flist));
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
