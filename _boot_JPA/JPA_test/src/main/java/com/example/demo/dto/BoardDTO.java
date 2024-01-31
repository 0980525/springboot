package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder                   
public class BoardDTO {
	
	//Entity : DB의 테이블 클래스 
	//DTO : 객체 클래스(=VO)
	
	private long bno;
	private String title;
	private String writer;
	private String content;
	private LocalDateTime regAt,modAt;
	
}
