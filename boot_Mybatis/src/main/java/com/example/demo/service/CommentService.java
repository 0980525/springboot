package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.CommentVO;

public interface CommentService {

	int post(CommentVO cvo);

	List<CommentVO> getList(long bno);

	int modify(CommentVO cvo);

}
