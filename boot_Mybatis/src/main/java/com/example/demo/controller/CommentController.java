package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping(value="/post",consumes="application/json",produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		int isOk = csv.post(cvo);
		return isOk > 0 ?
				new ResponseEntity<String>("1",HttpStatus.OK):
					new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
