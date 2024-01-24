package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardVO;

@Mapper
public interface BoardMapper {

	int insert(BoardVO bvo);

	List<BoardVO> selectList();

	BoardVO selectDetail(long bno);

	int update(BoardVO bvo);

	void delete(long bno);

}
