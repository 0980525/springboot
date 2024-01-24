package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.BoardVO;

public interface BoardService {

	int register(BoardVO bvo);

	List<BoardVO> getList();

	BoardVO getDetail(long bno);

	int modify(BoardVO bvo);

	void remove(long bno);

	

}
