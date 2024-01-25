package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;

public interface BoardService {

	int register(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(long bno);

	int modify(BoardVO bvo);

	void remove(long bno);

	int getTotalCount(PagingVO pgvo);

	

}
