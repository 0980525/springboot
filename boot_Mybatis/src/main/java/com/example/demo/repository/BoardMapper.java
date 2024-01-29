package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;

@Mapper
public interface BoardMapper {

	int insert(BoardVO bvo);

	List<BoardVO> selectList(PagingVO pgvo);

	BoardVO selectDetail(long bno);

	int update(BoardVO bvo);

	void delete(long bno);

	int selectTotalCnt(PagingVO pgvo);

	long getBno();

}
