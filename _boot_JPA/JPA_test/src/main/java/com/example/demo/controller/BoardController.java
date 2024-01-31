package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

	private final BoardService bsv;
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardDTO bdto) {
		log.info(">>>bdto>>>{}",bdto);
		Long bno = bsv.register(bdto);
		log.info(">>>bno>>>{}",bno);

		return "/index";
	}
	@GetMapping("/list")
	public void list(Model m) {
		List<BoardDTO>list = bsv.getList();
		m.addAttribute("list",list);
	}
}
