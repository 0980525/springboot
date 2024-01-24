package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.BoardVO;
import com.example.demo.repository.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;

	@Override
	public int register(BoardVO bvo) {
		// TODO Auto-generated method stub
		return mapper.insert(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		return mapper.selectList();
	}

	@Override
	public BoardVO getDetail(long bno) {
		return mapper.selectDetail(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		log.info(">>>>>bvo >>>>>{}",bvo);
		return mapper.update(bvo);
	}

	@Override
	public void remove(long bno) {
		mapper.delete(bno);
		
	}

	
}
