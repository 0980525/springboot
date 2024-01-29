package com.example.demo.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Handler.PagingHandler;
import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
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
	@GetMapping("/{bno}/{page}")
	@ResponseBody
	public PagingHandler list(@PathVariable("bno") long bno,@PathVariable("page")int page) {
		
		log.info(">>>>bno >>>> {}", bno,"/page >>{}",page);
		//list + ph.pgvo.qty 값 둘다 필요
		//비동기 => 한 객체만 전송 가능 => PagingHandler에 List 심어서 가져가기
		PagingVO pgvo = new PagingVO(page,5); //한 페이지에 5개씩 표시
		PagingHandler ph = csv.getList(bno,pgvo);
		return ph;
	}
	@PutMapping("/edit")
	@ResponseBody
	public String edit(@RequestBody CommentVO cvo) {
		log.info(">>CommentVO>>{}",cvo);
		int isOk = csv.modify(cvo);
		return isOk > 0?"1":"0";
	}
	
	@DeleteMapping("/del/{cno}")
	public ResponseEntity<String> remove(@PathVariable("cno")long cno) {
		log.info(">>>>>>>>comment delete controller >>>>>>>> ");
		int isOk = csv.remove(cno); 
		return isOk > 0? new ResponseEntity<String>("1",HttpStatus.OK):
			new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
