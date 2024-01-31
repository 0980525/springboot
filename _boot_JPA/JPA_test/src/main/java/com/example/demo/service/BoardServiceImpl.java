package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{
	private final BoardRepository repository;
	
	@Override
	public Long register(BoardDTO bdto) {
		return repository.save(convertDtoToEntity(bdto)).getBno();
	}

	@Override
	public List<BoardDTO> getList() {
		//DB에서 list로 리턴이 되기때문에 Board list 로 리턴 =>DTO 객체로 변환
		List<Board>list = repository.findAll(Sort.by(Sort.Direction.DESC,"bno"));
		List<BoardDTO> dtoList = new ArrayList<>();
		for(Board board:list) {
			dtoList.add(convertEntityToDto(board));
		}
		return dtoList;
	}

}
