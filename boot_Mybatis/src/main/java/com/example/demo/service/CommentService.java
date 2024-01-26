package com.example.demo.service;

import com.example.demo.Handler.PagingHandler;
import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;

public interface CommentService {

	int post(CommentVO cvo);

	PagingHandler getList(long bno, PagingVO pgvo);

	int modify(CommentVO cvo);

}
