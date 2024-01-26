package com.example.demo.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CommentVO;
import com.example.demo.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comment/*")
public class CommentController {

	private final CommentService csv;
	
	@PostMapping("/post")
	@ResponseBody
	public String post(@RequestBody CommentVO cvo){
		log.info(">>CommentVO>>{}",cvo);
		int isOk = csv.post(cvo);
		return isOk >0? "1":"0";			
	}
	@GetMapping("/{bno}")
	@ResponseBody
	public List<CommentVO> list(@PathVariable("bno") long bno) {
		List<CommentVO> list= csv.getList(bno);
		log.info(">>>>bno >>>> {}", bno);
		return list;
	}
}
