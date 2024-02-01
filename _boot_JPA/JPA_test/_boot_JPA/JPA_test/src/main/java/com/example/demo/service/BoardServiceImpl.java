package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
//  기존 list
//	@Override
//	public List<BoardDTO> getList() {
//		//DB에서 list로 리턴이 되기때문에 Board list 로 리턴 =>DTO 객체로 변환
//		List<Board>list = repository.findAll(Sort.by(Sort.Direction.DESC,"bno"));
//		List<BoardDTO> dtoList = new ArrayList<>();
//		for(Board board:list) {
//			dtoList.add(convertEntityToDto(board));
//		}
//		return dtoList;
//	}
	
	//page list
	@Override
	public Page<BoardDTO> getList(int page) {
		//pageNo =0 부터 시작
		Pageable pageable = PageRequest.of(page, 10, Sort.by("bno").descending());
		
		Page<Board>list = repository.findAll(pageable);
		
		Page<BoardDTO> dtoList = list.map(b->convertEntityToDto(b));
		
		return dtoList;
	}

	@Override
	public BoardDTO getDetail(Long bno) {
		//findById 아이디(기본키PK)를 주고 해당하는 객체를 리턴 
		// findById의 리턴 타입 Optional<Board>타입으로 리턴
		//Optional<Board> java.util 
		//Optional<T> : nullPointException 가 발생하지 않도록 도와줌
		//Optional.empty() : null인 경우 확인 가능 
		//Optional.isPresent : 값이 있는지 확인  
		Optional<Board> option = repository.findById(bno);
		
		return option.isPresent()?
				convertEntityToDto(option.get())
				: null;
	}

	@Override
	public Long modify(BoardDTO bdto) {
		// update : jpa는 업데이트 없음 / 저장-save/
		//기존의 객체를 가져와서 변경한 후 다시 저장 
		Optional<Board> option = repository.findById(bdto.getBno());
		if(option.isPresent()) {
			Board entity = option.get();
			//변경 내용 set
			entity.setTitle(bdto.getTitle());
			entity.setContent(bdto.getContent());
			//다시 저장
			return repository.save(entity).getBno();
		}
		
		return null;
	}

	@Override
	public void remove(Long bno) {
		// deleteById(id) : 
		repository.deleteById(bno);
		
	}

}
