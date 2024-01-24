package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardVO;

@Mapper
public interface BoardMapper {

	int insert(BoardVO bvo);

}
