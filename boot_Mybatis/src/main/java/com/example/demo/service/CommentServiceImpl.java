package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

	private final CommentMapper mapper;
}
