package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.CommentVO;

@Mapper
public interface CommentMapper {

	int post(CommentVO cvo);

}
