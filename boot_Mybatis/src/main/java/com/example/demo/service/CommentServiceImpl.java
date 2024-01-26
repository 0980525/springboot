package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Handler.PagingHandler;
import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.repository.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentMapper mapper;

	@Override
	public int post(CommentVO cvo) {
		log.info(">>>>>>>>comment post 01>>>>>>>>");
		return mapper.post(cvo);
	}

//	@Override
//	public List<CommentVO> getList(long bno) {
//		return mapper.getList(bno);
//		
//	}
	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// controller에서 처리해도 되지만, 처리속도가 더 빨라짐
		//totalCount
		int totalCount = mapper.bnoTotalCount(bno);
		//List
		List<CommentVO> list = mapper.getList(bno,pgvo);
		//합쳐서 ph로 생성
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		return ph;
	}

	@Override
	public int modify(CommentVO cvo) {
		// TODO Auto-generated method stub
		return mapper.modify(cvo);
	}

}
