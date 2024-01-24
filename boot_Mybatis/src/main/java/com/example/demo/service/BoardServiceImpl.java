package com.example.demo.service;

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
}
