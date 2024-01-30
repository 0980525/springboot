package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	private final FileMapper FileMapper;

	@Transactional
	@Override
	public void register(BoardDTO bdto) {
		int isOk = mapper.insert(bdto.getBvo());
		if(isOk > 0 && bdto.getFlist().size()>0) {
			long bno = mapper.getBno();
			
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= FileMapper.insert(fvo);
			}
		}
		
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		return mapper.selectList(pgvo);
	}

	@Transactional
	@Override
	public BoardDTO getDetail(long bno) {
		BoardDTO bdto=new BoardDTO();
		bdto.setBvo(mapper.selectDetail(bno));
		bdto.setFlist(FileMapper.getFileList(bno));
		return bdto;
	}

	@Transactional
	@Override
	public void modify(BoardDTO bdto) {
		log.info(">>>>>bvo >>>>>{}",bdto.getBvo());
		int isOk=mapper.update(bdto.getBvo());
		if(isOk >0 && bdto.getFlist().size()>0) {
			long bno = bdto.getBvo().getBno();
			for(FileVO fvo:bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *=FileMapper.insert(fvo);
			}
		}
		
	}

	@Override
	public void remove(long bno) {
		mapper.delete(bno);
		FileMapper.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return mapper.selectTotalCnt(pgvo);
	}

	
}
